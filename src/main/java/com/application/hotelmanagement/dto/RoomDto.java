package com.application.hotelmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    @NotBlank(message = "Room type is mandatory")
    @Size(min = 2, max = 50, message = "Room type must be between 2 to 50 characters")
    private String roomType;

    @NotNull(message = "Room number is mandatory")
    @Positive(message = "Room number must be greater than zero")
    private Integer roomNumber;

    @NotNull(message = "Room price is mandatory")
    @Positive(message = "Room price must be greater than zero")
    private Double price;
}
