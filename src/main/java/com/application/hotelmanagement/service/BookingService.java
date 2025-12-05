package com.application.hotelmanagement.service;

import com.application.hotelmanagement.dto.BookingDto;
import com.application.hotelmanagement.response.BookingResponse;
import com.application.hotelmanagement.response.BookingSummaryResponse;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    String createBooking(BookingDto booking, Long roomId);
    BookingResponse getBooking(Long bookingId);
    List<BookingSummaryResponse> getAllBookingsByDate(LocalDate bookingDate);
    String updateBooking(BookingDto bookingDto, Long bookingId);
    String deleteBooking(Long bookingId);
}
