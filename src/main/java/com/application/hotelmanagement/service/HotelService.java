package com.application.hotelmanagement.service;

import com.application.hotelmanagement.dto.HotelDto;
import com.application.hotelmanagement.model.Hotel;
import com.application.hotelmanagement.response.HotelResponse;
import java.util.List;

public interface HotelService {
     HotelResponse createHotel(HotelDto hotelDto);
     HotelResponse getHotelById(Long hotelId);
    List<HotelResponse> findAllHotels();
     HotelResponse updateHotel(HotelDto hotelDto, Long hotelId);
    void deleteHotel(Long hotelId);

    /**
     * This method is used for finding hotel by hotel ID for internal use and should not be deleted.
     * This method is for finding a hotel by its hotel ID and returns Hotel entity
     * If the hotel is not found, it throws a HotelNotFoundException.
     */
     Hotel findHotelById(Long hotelId);
}
