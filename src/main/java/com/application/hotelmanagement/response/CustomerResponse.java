package com.application.hotelmanagement.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private AddressResponse addressResponse;

}
