package com.application.hotelmanagement.service;

import com.application.hotelmanagement.dto.HotelDto;
import com.application.hotelmanagement.model.Hotel;
import com.application.hotelmanagement.response.HotelResponse;

import java.util.List;

public interface HotelService {
    String createHotel(HotelDto hotelDto);
    HotelResponse getHotelById(Long hotelId);
    List<HotelResponse> getHotelByName(String hotelName);
    List<HotelResponse> findAllHotels();
    String updateHotel(HotelDto hotelDto, Long hotelId);
    String deleteHotel(Long hotelId);

    /**
     * This method is used for booking room, it's for internal use and should not be deleted.
     * This method is for finding a hotel by its name.
     * If the hotel is not found, it throws a HotelNotFoundException.
     */
    List<Hotel> findHotelByHotelName(String hotelName);

    /**
     * This method is used for finding hotel by hotel ID for internal use and should not be deleted.
     * This method is for finding a hotel by its hotel ID and returns Hotel entity
     * If the hotel is not found, it throws a HotelNotFoundException.
     */
    Hotel findHotelById(Long hotelId);
}
