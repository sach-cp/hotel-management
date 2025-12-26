package com.application.hotelmanagement.service;

import com.application.hotelmanagement.dto.BookingDto;
import com.application.hotelmanagement.response.BookingResponse;
import com.application.hotelmanagement.response.BookingSummaryResponse;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingDto booking, Long roomId);
    BookingResponse getBooking(Long bookingId);
    List<BookingSummaryResponse> getAllBookingsByDate(LocalDate bookingDate);
    List<BookingSummaryResponse> getAllBookingsByEmail(String emailId);
    List<BookingSummaryResponse> getAllBookingsByPhoneNumber(String phoneNumber);
    String updateBooking(BookingDto bookingDto, Long bookingId);
    String deleteBooking(Long bookingId);

    List<BookingSummaryResponse> getAllBookingsByDateRange(LocalDate fromDate, LocalDate toDate);
}
