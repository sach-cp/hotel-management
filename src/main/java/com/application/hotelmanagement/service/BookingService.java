package com.application.hotelmanagement.service;

import com.application.hotelmanagement.dto.BookingDto;
import com.application.hotelmanagement.response.BookingResponse;
import com.application.hotelmanagement.response.BookingSummaryResponse;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingDto booking, Long roomId);
    BookingResponse getBooking(Long bookingId);
    BookingResponse updateBooking(BookingDto bookingDto, Long bookingId);
    void deleteBooking(Long bookingId);

    // Search bookings by various parameters like bookingDate, fromDate & toDate, phoneNumber
    List<BookingSummaryResponse> getAllBookingsByDate(LocalDate bookingDate);
    List<BookingSummaryResponse> getAllBookingsByDateRange(LocalDate fromDate, LocalDate toDate);
    List<BookingSummaryResponse> getAllBookingsByPhoneNumber(String phoneNumber);
}
