package com.application.hotelmanagement.service;

import com.application.hotelmanagement.dto.BookingDto;
import com.application.hotelmanagement.exception.BookingNotFoundException;
import com.application.hotelmanagement.exception.RoomNotAvailableException;
import com.application.hotelmanagement.mapper.BookingMapper;
import com.application.hotelmanagement.mapper.BookingSummaryMapper;
import com.application.hotelmanagement.model.Booking;
import com.application.hotelmanagement.model.BookingStatus;
import com.application.hotelmanagement.model.Hotel;
import com.application.hotelmanagement.model.Room;
import com.application.hotelmanagement.model.RoomStatus;
import com.application.hotelmanagement.repo.BookingRepository;
import com.application.hotelmanagement.response.BookingResponse;
import com.application.hotelmanagement.response.BookingSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final HotelService hotelService;
    private final RoomService roomService;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingDto bookingDto, Long roomId) {
        log.info("Attempting to book room for roomId: {}", roomId);
        boolean isAvailable = roomService.isRoomExistsAndAvailable(roomId, bookingDto.getCheckInDate(),
                bookingDto.getCheckOutDate());

        if (!isAvailable) {
            log.warn("Booking failed for roomId {} between {} and {}", roomId, bookingDto.getCheckInDate(),
                    bookingDto.getCheckOutDate());

            throw new RoomNotAvailableException("Room is already booked or confirmed for the selected dates");
        }

        Room room = roomService.findRoom(roomId);
        Hotel hotel = hotelService.findHotelById(room.getHotel().getHotelId());
        Booking finalBooking = BookingMapper.fromBookingDtoToEntity(bookingDto);
        finalBooking.setHotel(hotel);
        finalBooking.setRoom(room);
        if (bookingDto.getCheckInDate().isEqual(LocalDate.now())) {
            room.setRoomStatus(RoomStatus.CHECKED_IN);
            finalBooking.setStatus(BookingStatus.CHECKED_IN);
        } else {
            room.setRoomStatus(RoomStatus.CONFIRMED);
            finalBooking.setStatus(BookingStatus.CONFIRMED);
        }
        finalBooking.setCustomer(BookingMapper.fromCustomerDtoToEntity(bookingDto.getCustomerDto()));
        finalBooking.setBookingDate(LocalDate.now());
        finalBooking.setAdvanceAmount(bookingDto.getAdvanceAmount());
        finalBooking.setTotalAmount(bookingDto.getTotalAmount());
        finalBooking.setPurposeOfVisit(bookingDto.getPurposeOfVisit());
        finalBooking.setBookingDate(LocalDate.now());

        roomService.updateRoomStatus(room);
        Booking savedBooking = bookingRepository.save(finalBooking);
        log.info("Created new booking for room Id: {}", savedBooking.getRoom().getRoomId());
        return BookingMapper.fromEntityToResponse(savedBooking);
    }

    @Override
    public BookingResponse getBooking(Long bookingId) {
        Booking existingBooking = getExistingBooking(bookingId);
        if (existingBooking.getBookingId() != null) {
            log.info("Booking exists : {}", existingBooking);
            return BookingMapper.fromEntityToResponse(existingBooking);
        } else {
            log.info("No bookings found for bookingId: {}", bookingId);
            throw new BookingNotFoundException("Booking not found for bookingId: " + bookingId);
        }
    }

    @Override
    public List<BookingSummaryResponse> getAllBookingsByDate(LocalDate bookingDate) {
        log.info("Looking for bookings for the date: {}", bookingDate);
        List<Booking> bookings = bookingRepository.findAllBookingsByDate(bookingDate);
        if (CollectionUtils.isEmpty(bookings)) {
            log.info("No bookings found for Date: {}", bookingDate);
            return Collections.emptyList();
        } else {
            log.info("{} bookings found for the date: {}", bookings.size(), bookingDate);
            return bookings.stream()
                    .map(BookingSummaryMapper::fromEntityToResponse)
                    .toList();
        }
    }

    @Override
    @Transactional
    public String updateBooking(BookingDto bookingDto, Long bookingId) {
        log.info("Retrieving existing booking details for bookingId: {}", bookingId);
        Booking booking = getExistingBooking(bookingId);
        log.info("Looking for any conflicts");
        List<Booking> bookingConflicts = bookingRepository.findBookingConflicts(booking.getRoom().getRoomId(),
                bookingDto.getCheckInDate(),
                bookingDto.getCheckOutDate(),
                booking.getBookingId());

        if (bookingConflicts.isEmpty()) {
            log.info("No conflicts found, Attempting to update booking");
            booking.setCheckInDate(bookingDto.getCheckInDate());
            booking.setCheckOutDate(bookingDto.getCheckOutDate());
        } else {
            log.info("Booking cannot be updated due to conflict with other booking: {}",
                    bookingConflicts.stream().map(Booking::getBookingId));
            return "Cannot update booking due to a conflict with other booking";
        }
        return "Successfully updated your booking with booking id: " + booking.getBookingId();
    }

    @Override
    @Transactional
    public String deleteBooking(Long bookingId) {
        log.info("Retrieving existing booking for deleting with bookingId: {}", bookingId);
        Booking existingBooking = getExistingBooking(bookingId);
        log.info("Deleting booking for bookingId: {}", bookingId);
        bookingRepository.delete(existingBooking);
        return "Successfully deleted booking with booking id: " + bookingId;
    }

    @Override
    public List<BookingSummaryResponse> getAllBookingsByDateRange(LocalDate fromDate, LocalDate toDate) {
        List<Booking> bookings = bookingRepository.findAllBookingsByDateRange(fromDate, toDate);
        return bookings.stream().map(BookingSummaryMapper::fromEntityToResponse).toList();
    }

    private Booking getExistingBooking(Long bookingId) {
        log.info("Searching for booking with booking id: {}", bookingId);
        return bookingRepository.findById(bookingId).orElseThrow(() ->
                new BookingNotFoundException("Booking not found for id: " + bookingId));
    }

}
