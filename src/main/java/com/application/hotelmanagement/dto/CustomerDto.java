package com.application.hotelmanagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    public static final String PHONE_REGEX = "^(?:\\+91[\\s-]?)?(?:[6-9]\\d{9}|0\\d{2,4}([\\s-]?\\d{5,8}|[\\s-]?\\d{3,4}[\\s-]?\\d{4}))$";

    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 50, message = "First name must be between 2 to 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 1, max = 50, message = "Last name must be between 2 to 50 characters")
    private String lastName;

    @Valid
    @NotNull(message = "Address is mandatory")
    private AddressDto addressDto;

    @Pattern(regexp = PHONE_REGEX, message = "Please enter a valid phone number.\n" +
                    "Example: 9876543210 (mobile) or 080 1234 5678 (landline).")
    private String phoneNumber;
}
