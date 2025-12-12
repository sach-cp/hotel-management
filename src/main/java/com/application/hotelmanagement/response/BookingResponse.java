package com.application.hotelmanagement.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookingResponse {
    private Long bookingId;
    private String hotelName;
    private String roomType;
    private Integer roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate bookingDate;
    private Integer numberOfPersons;
    private Double price;
    private String status;
    private String purposeOfVisit;
    private Double advanceAmount;
    private String paymentStatus;
    private CustomerResponse customerResponse;
}
