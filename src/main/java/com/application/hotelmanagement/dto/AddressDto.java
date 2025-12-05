package com.application.hotelmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    @NotBlank(message = "Address details are mandatory")
    @Size(min = 2, max = 50, message = "Address details must be between 2 to 50 characters")
    private String addressDetails;

    @NotBlank(message = "City is mandatory")
    @Size(min = 2, max = 50, message = "City must be between 2 to 50 characters")
    private String city;

    @NotBlank(message = "State is mandatory")
    @Size(min = 2, max = 50, message = "State must be between 2 to 50 characters")
    private String state;

    @NotBlank(message = "Country is mandatory")
    @Size(min = 2, max = 50, message = "Country must be between 2 to 50 characters")
    private String country;

    @NotBlank(message = "Pin code is mandatory")
    @Size(min = 6, max = 6, message = "Pin code must be 6 characters")
    private String pinCode;
}
