package com.application.hotelmanagement.service;

import com.application.hotelmanagement.dto.HotelDto;
import com.application.hotelmanagement.exception.HotelNotFoundException;
import com.application.hotelmanagement.mapper.AddressMapper;
import com.application.hotelmanagement.mapper.HotelMapper;
import com.application.hotelmanagement.model.Hotel;
import com.application.hotelmanagement.repo.HotelRepository;
import com.application.hotelmanagement.response.HotelResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    @Transactional
    @CacheEvict(value = "hotelsCache", key = "'allHotels'")
    public String createHotel(HotelDto hotelDto) {
        Hotel hotel = HotelMapper.fromDtoToEntity(hotelDto);
        log.info("Saving new hotel: {}", hotel.getHotelName());
        Hotel savedHotel = hotelRepository.save(hotel);
        log.info("Hotel saved successfully with hotelId: {}", savedHotel.getHotelId());
        return "Successfully created new hotel";
    }

    @Override
    public HotelResponse getHotelById(Long hotelId) {
        log.info("Searching for hotel with hotelId: {}", hotelId);
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() ->
                new HotelNotFoundException("Hotel with hotelId: " + hotelId + " not found"));
        log.info("Found hotel with hotelId: {}", hotelId);
        return HotelMapper.fromEntityToResponse(hotel);
    }

    @Override
    public List<HotelResponse> getHotelByName(String hotelName) {
        log.info("Searching for hotel with hotel name: {}", hotelName);
        List<Hotel> existingHotel = findHotelByHotelName(hotelName);
        log.info("List of hotels: {}", existingHotel.stream());
        return existingHotel.stream()
                .map(HotelMapper::fromEntityToResponse)
                .toList();
    }

    @Override
    @Cacheable(value = "hotelsCache", key = "'allHotels'")
    public List<HotelResponse> findAllHotels() {
        log.info("Fetching all hotels");
        return hotelRepository.findAll()
                .stream()
                .map(HotelMapper::fromEntityToResponse)
                .toList();
    }

    @Override
    @Transactional
    public String updateHotel(HotelDto hotelDto, Long hotelId) {
        log.info("Updating hotel with hotel name: {}", hotelId);
        Hotel existingHotel = findHotelById(hotelId);
        existingHotel.setAddress(AddressMapper.fromDtoToEntity(hotelDto.getAddressDto()));
        existingHotel.setPhoneNumber(hotelDto.getPhoneNumber());
        existingHotel.setEmailId(hotelDto.getEmailId());
        log.info("Updating hotel with updated values for hotelId: {}", existingHotel.getHotelId());
        hotelRepository.save(existingHotel);
        return existingHotel.getHotelName() + " hotel successfully updated";
    }

    @Override
    @Transactional
    public String deleteHotel(Long hotelId) {
        log.info("Deleting hotel with hotelId: {}", hotelId);
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() ->
                new HotelNotFoundException("Hotel with hotelId: " + hotelId + " not found"));
        hotelRepository.delete(hotel);
        log.info("Successfully deleted hotel with hotelId: {}", hotelId);
        return hotel.getHotelName() + " hotel successfully deleted";
    }

    @Override
    public List<Hotel> findHotelByHotelName(String hotelName) {
        log.info("Finding hotel by hotel name: {}", hotelName);
        return hotelRepository.findByHotelName(hotelName)
                .orElseThrow(() -> new HotelNotFoundException(hotelName + " hotel not found"))
                .stream()
                .toList();
    }

    @Override
    public Hotel findHotelById(Long hotelId) {
        log.info("Finding hotel by hotel ID: {}", hotelId);
        return hotelRepository.findById(hotelId).orElseThrow(() ->
                new HotelNotFoundException("Hotel with hotel Id: " + hotelId + " not found"));
    }
}
