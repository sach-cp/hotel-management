package com.application.hotelmanagement.service;

import com.application.hotelmanagement.dto.BookingDto;
import com.application.hotelmanagement.exception.BookingNotFoundException;
import com.application.hotelmanagement.mapper.BookingMapper;
import com.application.hotelmanagement.mapper.BookingSummaryMapper;
import com.application.hotelmanagement.mapper.CustomerMapper;
import com.application.hotelmanagement.model.Booking;
import com.application.hotelmanagement.model.Room;
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

    private final RoomService roomService;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public String createBooking(BookingDto bookingDto, Long roomId) {
        log.info("Attempting to book room for roomId: {}", roomId);
        Room room = roomService.findRoom(bookingDto.getRoomId());
        Booking finalBooking = BookingMapper.fromDtoToEntity(bookingDto);
        finalBooking.setHotel(room.getHotel());
        finalBooking.setRoom(room);
        finalBooking.setCustomer(CustomerMapper.fromDtoToEntity(bookingDto.getCustomerDto(),
                bookingDto.getCustomerDto().getAddressDto()));
        finalBooking.setBookingStatus("BOOKED");
        finalBooking.setBookingDate(LocalDate.now());
        finalBooking.setAdvanceAmount(bookingDto.getAdvanceAmount());
        finalBooking.setTotalAmount(bookingDto.getTotalAmount());
        finalBooking.setPurposeOfVisit(bookingDto.getPurposeOfVisit());
        finalBooking.setBookingDate(LocalDate.now());
        Booking savedBooking = bookingRepository.save(finalBooking);
        log.info("Created new booking for hotel: {} with room number: {}", savedBooking.getHotel().getHotelName(),
                room.getRoomNumber());
        return "Successfully booked with booking id: " + savedBooking.getBookingId();
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
        List<Booking> bookings = bookingRepository.findAllBookingsByDate(bookingDate);
        if (CollectionUtils.isEmpty(bookings)) {
            log.info("No bookings found for Date: {}", bookingDate);
            return Collections.emptyList();
        } else return bookings.stream()
                .map(BookingSummaryMapper::fromEntityToResponse)
                .toList();
    }

    @Override
    @Transactional
    public String updateBooking(BookingDto bookingDto, Long bookingId) {
        Booking existingBooking = getExistingBooking(bookingId);
        List<Booking> bookingConflicts = bookingRepository.findBookingConflicts(
                existingBooking.getRoom().getRoomId(),
                bookingDto.getCheckInDate(),
                bookingDto.getCheckOutDate(),
                existingBooking.getBookingId());

        if (!bookingConflicts.isEmpty()) {
            return "Cannot update booking due to a conflict with existing booking: " + bookingConflicts
                    .stream().map(Booking::getBookingId);
        } else {
            existingBooking.setCheckInDate(bookingDto.getCheckInDate());
            existingBooking.setCheckOutDate(bookingDto.getCheckOutDate());
        }

        return "Successfully updated your booking with booking id: " + existingBooking.getBookingId();
    }

    @Override
    @Transactional
    public String deleteBooking(Long bookingId) {
        Booking existingBooking = getExistingBooking(bookingId);
        bookingRepository.delete(existingBooking);
        return "Successfully deleted booking with booking id: " + bookingId;
    }

    private Booking getExistingBooking(Long bookingId) {
        log.info("Searching for booking with booking id: {}", bookingId);
        return bookingRepository.findById(bookingId).orElseThrow(() ->
                new BookingNotFoundException("Booking not found for id: " + bookingId));
    }

}
