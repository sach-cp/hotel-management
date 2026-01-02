package com.application.hotelmanagement.controller;

import com.application.hotelmanagement.dto.BookingDto;
import com.application.hotelmanagement.response.BookingResponse;
import com.application.hotelmanagement.response.BookingSummaryResponse;
import com.application.hotelmanagement.service.BookingService;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/rooms/{roomId}")
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingDto bookingDto,
                                                         @PathVariable Long roomId) {
        BookingResponse bookingResponse = bookingService.createBooking(bookingDto, roomId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/api/v1/bookings/" + bookingResponse.getBookingId())
                .body(bookingResponse);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBooking(bookingId));
    }

    // Search by various parameters like bookingDate, fromDate & toDate, phoneNumber
    @GetMapping("/search")
    public ResponseEntity<List<BookingSummaryResponse>> getBookings(
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate bookingDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate toDate,
            @RequestParam(required = false) String phoneNumber) {
        if (bookingDate != null) {
            return ResponseEntity.ok(bookingService.getAllBookingsByDate(bookingDate));
        } else if (fromDate != null && toDate != null) {
            if (fromDate.isAfter(toDate)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fromDate must be before or equal to toDate");
            }
            return ResponseEntity.ok(bookingService.getAllBookingsByDateRange(fromDate, toDate));
        } else if (StringUtils.isNotBlank(phoneNumber)) {
            return ResponseEntity.ok(bookingService.getAllBookingsByPhoneNumber(phoneNumber));
        } else {
            throw new IllegalArgumentException("Invalid parameters");
        }
    }


    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> updateBooking(@Valid @RequestBody BookingDto bookingDto,
                                                         @PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.updateBooking(bookingDto, bookingId));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

}
