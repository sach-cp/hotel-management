package com.application.hotelmanagement.dto;

import com.application.hotelmanagement.validation.ValidDateRange;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Data
@Validated
@ValidDateRange
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    @NotNull(message = "RoomId cannot be null")
    private Long roomId;

    @NotNull(message = "Number of persons is mandatory")
    @Min(value = 1, message = "Number of persons must be at least 1")
    @Max(value = 3, message = "Number of persons must not exceed 3")
    private Integer numberOfPersons;

    @Valid
    @NotNull(message = "Customer details are mandatory")
    private CustomerDto customerDto;

    @NotNull(message = "Check-in date cannot be null")
    @FutureOrPresent(message = "Check-in date must be today or in the future")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date cannot be null")
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @NotBlank(message = "Purpose of visit is mandatory")
    @Size(min = 2, max = 100, message = "Purpose of visit must be between 2 to 100 characters")
    private String purposeOfVisit;

    @NotNull(message = "Total amount cannot be null")
    private Double totalAmount;

    @NotNull(message = "Advance amount cannot be null")
    private Double advanceAmount;

    @NotBlank(message = "Payment mode is mandatory")
    @Size(min = 2, max = 20, message = "Payment mode must be between 2 to 20 characters")
    private String paymentMode;

    @NotBlank(message = "Payment status is mandatory")
    @Size(min = 2, max = 20, message = "Payment status must be between 2 to 20 characters")
    private String paymentStatus;
}
