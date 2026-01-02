package com.application.hotelmanagement.repo;

import com.application.hotelmanagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    // Find a room by room number and hotelId
    Room findByRoomNumberAndHotel_HotelId(Integer roomNumber, Long hotelId);

    // Find a room by roomId and hotelId
    Room findByRoomIdAndHotel_HotelId(Long roomId, Long hotelId);

    List<Room> findAllRoomsByHotel_HotelId(Long hotelId);

    Optional<Room> findByRoomId(Long roomId);
}
