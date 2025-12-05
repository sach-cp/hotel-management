package com.application.hotelmanagement.controller;

import com.application.hotelmanagement.dto.HotelDto;
import com.application.hotelmanagement.response.HotelResponse;
import com.application.hotelmanagement.service.HotelService;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class HotelController {

    private HotelService hotelService;

    @PostMapping("/hotels")
    public String createHotel(@Valid @RequestBody HotelDto hotelDto) {
        return hotelService.createHotel(hotelDto);
    }

    @GetMapping("/hotels/{Id}")
    public HotelResponse getHotelById(@PathVariable(name = "Id") Long hotelId) {
        return hotelService.getHotelById(hotelId);
    }

    @GetMapping("/hotels")
    public List<HotelResponse> getHotels(@RequestParam(required = false) String hotelName) {
        if (StringUtils.isNotBlank(hotelName)) return hotelService.getHotelByName(hotelName);
        else return hotelService.findAllHotels();
    }

    @PutMapping("/hotels/{id}")
    public String updateHotel(@Valid @RequestBody HotelDto hotelDto, @PathVariable(name = "Id") Long hotelId) {
        return hotelService.updateHotel(hotelDto, hotelId);
    }

    @DeleteMapping("/hotels/{id}")
    public String deleteHotel(@PathVariable(name = "Id") Long hotelId) {
        return hotelService.deleteHotel(hotelId);
    }
}
