package com.application.hotelmanagement.repo;

import com.application.hotelmanagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByRoomNumberAndHotel_HotelId(Integer roomNumber, Long hotelId);
    Optional<List<Room>> findAllRoomsByHotel_HotelId(Long hotelId);

    @Query("SELECT r FROM Room r " +
            "WHERE r.hotel.id = :hotelId AND r.id NOT IN " +
            "(SELECT b.room.id FROM Booking b " +
            "WHERE b.bookingStatus IN ('CONFIRMED','BOOKED') AND b.checkInDate < :checkOutDate AND " +
            "b.checkOutDate > :checkInDate)")
    List<Room> findAvailableRooms(@Param("hotelId") Long hotelId,
                                  @Param("checkInDate") LocalDate checkInDate,
                                  @Param("checkOutDate") LocalDate checkOutDate);

    Optional<Room> findByRoomId(Long roomId);
}
