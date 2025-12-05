package com.application.hotelmanagement.dto;

import com.application.hotelmanagement.model.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
public class HotelDto {

    public static final String PHONE_REGEX = "^(?:\\+91[\\s-]?)?(?:[6-9]\\d{9}|0\\d{2,4}([\\s-]?\\d{5,8}|[\\s-]?\\d{3,4}[\\s-]?\\d{4}))$";

    @NotBlank(message = "Hotel name is mandatory")
    @Size(min = 2, max = 50, message = "Hotel name must be between 2 to 50 characters")
    private String hotelName;

    @Valid
    @NotNull(message = "Hotel address is mandatory")
    private AddressDto addressDto;

    @Pattern(regexp = PHONE_REGEX,
            message = "Please enter a valid phone number.\n" +
                    "Example: 9876543210 (mobile) or 080 1234 5678 (landline).")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email ID is mandatory")
    private String emailId;
}
