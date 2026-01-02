package com.application.hotelmanagement.controller;

import com.application.hotelmanagement.dto.RoomDto;
import com.application.hotelmanagement.response.RoomResponse;
import com.application.hotelmanagement.service.RoomService;
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
@RequestMapping("/api/v1/hotels")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Room Management Service is up and running!");
    }

    @PostMapping("/{hotelId}/rooms")
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomDto roomDto, @PathVariable Long hotelId) {
        RoomResponse roomResponse = roomService.createRoom(roomDto, hotelId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, "/api/v1/hotels/" + hotelId + "/rooms/"
                        + roomResponse.getRoomId())
                .body(roomResponse);
    }

    @GetMapping("/{hotelId}/rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms(@PathVariable Long hotelId) {
        return ResponseEntity.ok(roomService.getAllRooms(hotelId));
    }

    @PutMapping("/{hotelId}/rooms/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@Valid @RequestBody RoomDto roomDto, @PathVariable Long hotelId,
                                                   @PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.updateRoom(roomDto, hotelId, roomId));
    }

    @DeleteMapping("/{hotelId}/rooms/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long hotelId, @PathVariable Long roomId) {
        roomService.deleteRoom(hotelId, roomId);
        return ResponseEntity.noContent().build();
    }
}
