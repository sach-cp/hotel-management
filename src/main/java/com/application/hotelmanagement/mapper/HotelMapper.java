package com.application.hotelmanagement.mapper;

import com.application.hotelmanagement.dto.AddressDto;
import com.application.hotelmanagement.dto.HotelDto;
import com.application.hotelmanagement.model.Address;
import com.application.hotelmanagement.model.Hotel;
import com.application.hotelmanagement.response.HotelResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HotelMapper {
    public static Hotel fromDtoToEntity(HotelDto hotelDto) {
        Address address = fromAddressDtoToEntity(hotelDto.getAddressDto());
        return Hotel.builder()
                .hotelName(hotelDto.getHotelName())
                .address(address)
                .phoneNumber(hotelDto.getPhoneNumber())
                .emailId(hotelDto.getEmailId())
                .build();
    }

    public static Address fromAddressDtoToEntity(AddressDto addressDto) {
        return Address.builder()
                .addressDetails(addressDto.getAddressDetails())
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .country(addressDto.getCountry())
                .pinCode(addressDto.getPinCode())
                .build();
    }

    public static HotelResponse fromEntityToResponse(Hotel hotel) {
        return HotelResponse.builder()
                .hotelId(hotel.getHotelId())
                .hotelName(hotel.getHotelName())
                .address(AddressMapper.fromEntityToResponse(hotel.getAddress()))
                .phoneNumber(hotel.getPhoneNumber())
                .emailId(hotel.getEmailId())
                .build();
    }
}
