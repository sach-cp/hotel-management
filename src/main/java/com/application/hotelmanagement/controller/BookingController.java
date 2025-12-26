package com.application.hotelmanagement.controller;

import com.application.hotelmanagement.dto.BookingDto;
import com.application.hotelmanagement.response.BookingResponse;
import com.application.hotelmanagement.response.BookingSummaryResponse;
import com.application.hotelmanagement.service.BookingService;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private BookingService bookingService;

    @PostMapping("/rooms/{roomId}")
    public BookingResponse createBooking(@Valid @RequestBody BookingDto bookingDto, @PathVariable(name = "roomId") Long roomId) {
        return bookingService.createBooking(bookingDto, roomId);
    }

    // Search by Booking ID
    @GetMapping("/{bookingId}")
    public BookingResponse getBooking(@PathVariable Long bookingId) {
        return bookingService.getBooking(bookingId);
    }

    // Search by various parameters like bookingDate, fromDate & toDate, emailId, phoneNumber
    @GetMapping("/search")
    public List<BookingSummaryResponse> getBookings(@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                    LocalDate bookingDate,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                    LocalDate fromDate,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy")
                                                    LocalDate toDate,
                                                    @RequestParam(required = false) String emailId,
                                                    @RequestParam(required = false) String phoneNumber) {
        if (bookingDate != null) {
            return bookingService.getAllBookingsByDate(bookingDate);
        } else if (fromDate != null && toDate != null) {
            if (fromDate.isAfter(toDate)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fromDate must be before or equal to toDate");
            }
            return bookingService.getAllBookingsByDateRange(fromDate, toDate);
        } else if (StringUtils.isNotBlank(emailId)) {
            return bookingService.getAllBookingsByEmail(emailId);
        } else if (StringUtils.isNotBlank(phoneNumber)) {
            return bookingService.getAllBookingsByPhoneNumber(phoneNumber);
        } else {
            throw new IllegalArgumentException("Invalid parameters");
        }
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
