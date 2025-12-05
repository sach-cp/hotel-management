package com.application.hotelmanagement.service;

import com.application.hotelmanagement.dto.RoomDto;
import com.application.hotelmanagement.model.Room;
import com.application.hotelmanagement.response.RoomResponse;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    String createRoom(RoomDto roomDto, Long hotelId);
    RoomResponse getRoom(Long hotelId, Integer roomNumber);
    RoomResponse getRoomById(Long hotelId, Long roomId);
    List<RoomResponse> getAllRooms(Long hotelId);
    List<RoomResponse> getAvailableRooms(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate);
    String updateRoom(RoomDto roomDto, Long hotelId, Integer roomNumber);
    String deleteRoom(Long hotelId, Integer roomNumber);

    /**
     * Do not delete this method as it is called in createNewBooking method in Booking Service
     * If found returns a room which is associated with hotel
     */
    Room findRoom(Long roomId);

    /**
     *
     * @param roomId is the parameter to search room existence and
     *               checks availability by checkIn and checkOut dates.
     * @return true/false based on room existence and availability.
     */
    boolean isRoomExistsAndAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut);

    RoomResponse getByRoomId(Long roomId);
}
