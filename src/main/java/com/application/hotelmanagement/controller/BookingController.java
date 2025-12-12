package com.application.hotelmanagement.controller;

import com.application.hotelmanagement.dto.BookingDto;
import com.application.hotelmanagement.response.BookingResponse;
import com.application.hotelmanagement.response.BookingSummaryResponse;
import com.application.hotelmanagement.service.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private BookingService bookingService;

    @PostMapping("/rooms/{roomId}")
    public String createBooking(@Valid @RequestBody BookingDto bookingDto, @PathVariable(name = "roomId") Long roomId) {
        return bookingService.createBooking(bookingDto, roomId);
    }

    @GetMapping("/{bookingId}")
    public BookingResponse getBooking(@PathVariable Long bookingId) {
        return bookingService.getBooking(bookingId);
    }

    @GetMapping("/by-date")
    public List<BookingSummaryResponse> getAllBookingsByDate(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                             LocalDate bookingDate) {
        return bookingService.getAllBookingsByDate(bookingDate);
    }

    @GetMapping("/by-range")
    public List<BookingSummaryResponse> getAllBookingsByDateRange(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                                  LocalDate fromDate,
                                                                  @RequestParam @DateTimeFormat(pattern = "dd-MM-yyy")
                                                                  LocalDate toDate) {
        return bookingService.getAllBookingsByDateRange(fromDate, toDate);
    }

    @PutMapping("/{bookingId}")
    public String updateBooking(@Valid @RequestBody BookingDto bookingDto, @PathVariable Long bookingId) {
        return bookingService.updateBooking(bookingDto, bookingId);
    }

    @DeleteMapping("/{bookingId}")
    public String deleteBooking(@PathVariable Long bookingId) {
        return bookingService.deleteBooking(bookingId);
    }

}
