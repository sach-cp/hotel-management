package com.application.hotelmanagement.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BookingResponse {
    private String hotelName;
    private Long bookingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate bookingDate;
    private Integer numberOfPersons;
    private String roomType;
    private Integer roomNumber;
    private Double price;
    private String status;
    private String purposeOfVisit;
    private Double advanceAmount;
    private String paymentStatus;
    private CustomerResponse customerResponse;

}
