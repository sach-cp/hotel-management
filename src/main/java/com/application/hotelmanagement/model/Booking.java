package com.application.hotelmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long hotelId;
    private String hotelName;
    private Long roomId;
    private String roomType;
    private Integer roomNumber;
    private Double roomBookingPrice;

    @Embedded
    private Customer customer;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "number_of_persons", nullable = false, length = 10)
    private Integer numberOfPersons;

    @Column(name = "booking_status", nullable = false, length = 20)
    private String bookingStatus;

    @Column(name = "purpose_of_visit", length = 100)
    private String purposeOfVisit;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "advance_amount")
    private Double advanceAmount;

    @Column(name = "payment_mode", nullable = false, length = 20)
    private String paymentMode;

    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus;
}
