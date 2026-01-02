package com.application.hotelmanagement.service;

import com.application.hotelmanagement.dto.RoomDto;
import com.application.hotelmanagement.model.Room;
import com.application.hotelmanagement.response.RoomResponse;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    RoomResponse createRoom(RoomDto roomDto, Long hotelId);
    List<RoomResponse> getAllRooms(Long hotelId);
    RoomResponse updateRoom(RoomDto roomDto, Long hotelId, Long roomId);
    void deleteRoom(Long hotelId, Long roomId);

    /**
     * Do not delete this method as it is called in createNewBooking method in Booking Service
     * If found returns a room which is associated with hotel
     */
    Room findRoom(Long roomId);

    /**
     * Do not delete this method as it is called in createNewBooking method in Booking Service
     * If exists and available for booking returns a true value else a false value
     */
    Boolean isRoomExistsAndAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut);

    /**
     * Do not delete this method as it is called in createNewBooking method in Booking Service
     * It updates the room status in the room entity while booking a room
     */
    Room updateRoomStatus(Room room);
}
