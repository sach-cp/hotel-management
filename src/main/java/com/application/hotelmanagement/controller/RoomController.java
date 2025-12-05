package com.application.hotelmanagement.controller;

import com.application.hotelmanagement.dto.RoomDto;
import com.application.hotelmanagement.response.RoomResponse;
import com.application.hotelmanagement.service.RoomService;
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

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@AllArgsConstructor
public class RoomController {

    private RoomService roomService;

    @PostMapping("/{hotelId}/rooms")
    public String createRoom(@Valid @RequestBody RoomDto roomDto, @PathVariable Long hotelId) {
        return roomService.createRoom(roomDto, hotelId);
    }

    // GET ONE ROOM BY ROOM NUMBER
    @GetMapping("/{hotelId}/rooms/{roomNumber}")
    public RoomResponse getRoom(@PathVariable Long hotelId, @PathVariable Integer roomNumber) {
        return roomService.getRoom(hotelId, roomNumber);
    }

    // GET ONE ROOM BY ROOM ID
    @GetMapping("/{hotelId}/rooms/{roomId}")
    public RoomResponse getRoomById(@PathVariable Long hotelId, @PathVariable Long roomId) {
        return roomService.getRoomById(hotelId, roomId);
    }

    // GET ALL ROOMS OF A HOTEL
    @GetMapping("/{hotelId}/rooms")
    public List<RoomResponse> getAllRooms(@PathVariable Long hotelId) { return roomService.getAllRooms(hotelId); }

    // GET AVAILABLE ROOMS FOR A HOTEL
    @GetMapping("/{hotelId}/rooms/available")
    public List<RoomResponse> getAvailableRooms(@PathVariable Long hotelId,
                                                @RequestParam LocalDate checkInDate,
                                                @RequestParam LocalDate checkOutDate) {
        return roomService.getAvailableRooms(hotelId, checkInDate, checkOutDate);
    }

    // UPDATE ROOM
    @PutMapping("/{hotelId}/rooms/{roomNumber}")
    public String updateRoom(@Valid @RequestBody RoomDto roomDto, @PathVariable Long hotelId,
                             @PathVariable Integer roomNumber) {
        return roomService.updateRoom(roomDto, hotelId, roomNumber);
    }

    // DELETE ROOM
    @DeleteMapping("/{hotelId}/rooms/{roomNumber}")
    public String deleteRoom(@PathVariable Long hotelId, @PathVariable Integer roomNumber) {
        return roomService.deleteRoom(hotelId, roomNumber);
    }

    // Booking client calls this method
    @GetMapping("/rooms/{roomId}/availability")
    public boolean isRoomExistsAndAvailable(@PathVariable Long roomId,
                                            @RequestParam LocalDate checkInDate,
                                            @RequestParam LocalDate checkOutDate) {
        return roomService.isRoomExistsAndAvailable(roomId, checkInDate, checkOutDate);
    }

    @GetMapping("/rooms/{roomId}")
    public RoomResponse getByRoomId(@PathVariable Long roomId) {
        return roomService.getByRoomId(roomId);
    }
}
