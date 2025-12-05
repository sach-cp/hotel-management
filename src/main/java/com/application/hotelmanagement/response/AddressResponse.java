package com.application.hotelmanagement.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {

    private String city;
    private String state;
    private String country;
    private String pinCode;
    private String addressDetails;

}
