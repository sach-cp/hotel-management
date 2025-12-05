package com.application.hotelmanagement.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelResponse {
    private Long hotelId;
    private String hotelName;
    private AddressResponse address;
    private String phoneNumber;
    private String emailId;
}
