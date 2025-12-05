package com.application.hotelmanagement.mapper;

import com.application.hotelmanagement.dto.AddressDto;
import com.application.hotelmanagement.model.Address;
import com.application.hotelmanagement.response.AddressResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AddressMapper {

    public static Address fromDtoToEntity(AddressDto addressDto) {
        return Address.builder()
                .addressDetails(addressDto.getAddressDetails())
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .country(addressDto.getCountry())
                .pinCode(addressDto.getPinCode())
                .build();
    }

    public AddressResponse fromEntityToResponse(Address address) {
        return AddressResponse.builder()
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .pinCode(address.getPinCode())
                .addressDetails(address.getAddressDetails())
                .build();
    }
}
