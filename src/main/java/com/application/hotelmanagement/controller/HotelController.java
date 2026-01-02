package com.application.hotelmanagement.controller;

import com.application.hotelmanagement.dto.HotelDto;
import com.application.hotelmanagement.response.HotelResponse;
import com.application.hotelmanagement.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Hotel Management Service is up and running!");
    }

    @PostMapping("/hotels")
    public ResponseEntity<HotelResponse> createHotel(@Valid @RequestBody HotelDto hotelDto) {
        HotelResponse hotelResponse = hotelService.createHotel(hotelDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/api/v1/hotels/" + hotelResponse.getHotelId())
                .body(hotelResponse);
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable Long hotelId) {
        return ResponseEntity.ok(hotelService.getHotelById(hotelId));
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelResponse>> getAllHotels() {
        return ResponseEntity.ok(hotelService.findAllHotels());
    }

    @PutMapping("/hotels/{hotelId}")
    public ResponseEntity<HotelResponse> updateHotel(@Valid @RequestBody HotelDto hotelDto,
                                                     @PathVariable Long hotelId) {
        return ResponseEntity.ok(hotelService.updateHotel(hotelDto, hotelId));
    }

    @DeleteMapping("/hotels/{hotelId}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long hotelId) {
        hotelService.deleteHotel(hotelId);
        return ResponseEntity.noContent().build();
    }
}
