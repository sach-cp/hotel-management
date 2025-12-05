package com.application.hotelmanagement.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookingSummaryResponse {
    private Long bookingId;
    private String hotelName;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
