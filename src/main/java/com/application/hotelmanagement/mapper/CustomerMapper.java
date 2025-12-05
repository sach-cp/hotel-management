package com.application.hotelmanagement.mapper;

import com.application.hotelmanagement.dto.AddressDto;
import com.application.hotelmanagement.dto.CustomerDto;
import com.application.hotelmanagement.model.Customer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerMapper {

    public static Customer fromDtoToEntity(CustomerDto customerDto, AddressDto addressDto) {
        return Customer.builder()
                .address(AddressMapper.fromDtoToEntity(addressDto))
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .phoneNumber(customerDto.getPhoneNumber())
                .build();
    }
}
