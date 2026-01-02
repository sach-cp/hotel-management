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
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    @Transactional
    @CacheEvict(value = "hotels", key = "'allHotels'", allEntries = true)
    public HotelResponse createHotel(HotelDto hotelDto) {
        log.info("Saving new hotel: {}", hotelDto.getHotelName());
        Hotel hotel = HotelMapper.fromDtoToEntity(hotelDto);
        Hotel savedHotel = hotelRepository.save(hotel);
        log.info("Hotel saved successfully with HotelId: {}", savedHotel.getHotelId());
        return HotelMapper.fromEntityToResponse(savedHotel);
    }

    @Override
    public HotelResponse getHotelById(Long hotelId) {
        log.info("Searching for hotel with hotelId: {}", hotelId);
        Hotel hotel = findHotelById(hotelId);
        log.info("Hotel found with hotelId: {}", hotelId);
        return HotelMapper.fromEntityToResponse(hotel);
    }

    @Override
    @Cacheable(value = "hotels", key = "'allHotels'")
    public List<HotelResponse> findAllHotels() {
        log.info("Fetching all hotels");
        List<Hotel> hotels = hotelRepository.findAll();
        if (CollectionUtils.isEmpty(hotels)) {
            log.info("No hotels found in the database");
            throw new HotelNotFoundException("No hotels found");
        }
        log.info("Hotels found: {}", hotels.size());
        return hotels.stream().map(HotelMapper::fromEntityToResponse).toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = "hotels", key = "'allHotels'", allEntries = true)
    public HotelResponse updateHotel(HotelDto hotelDto, Long hotelId) {
        log.info("Updating hotel with ID: {}", hotelId);
        Hotel existingHotel = findHotelById(hotelId);
        log.info("Hotel: {} found with hotelId: {}", existingHotel.getHotelName(), hotelId);
        Hotel updatedHotel = HotelMapper.fromDtoToEntity(hotelDto);
        hotelRepository.save(updatedHotel);
        log.info("Hotel: {} updated successfully with hotelId: {}", updatedHotel.getHotelName(), hotelId);
        return HotelMapper.fromEntityToResponse(updatedHotel);
    }

    @Override
    @Transactional
    @CacheEvict(value = "hotels", key = "'allHotels'", allEntries = true)
    public void deleteHotel(Long hotelId) {
        log.info("Deleting hotel with ID: {}", hotelId);
        Hotel existingHotel = findHotelById(hotelId);
        log.info("Hotel: {} found with hotelId: {}", existingHotel.getHotelName(), hotelId);
        hotelRepository.delete(existingHotel);
        log.info("Hotel: {} deleted successfully with hotelId: {}", existingHotel.getHotelName(), hotelId);
    }

    /**
     * This method is used for finding hotel by hotel ID for internal use and should not be deleted.
     * This method is for finding a hotel by its hotel ID and returns Hotel entity
     * If the hotel is not found, it throws a HotelNotFoundException.
     */
    @Override
    public Hotel findHotelById(Long hotelId) {
        log.info("Finding hotel by Id: {}", hotelId);
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with Id: " + hotelId));
    }
}
