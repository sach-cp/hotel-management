package com.application.hotelmanagement.repo;

import com.application.hotelmanagement.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b " +
            "WHERE b.room.roomId = :roomId " +
            "AND b.bookingId <> :currentBookingId " +
            "AND ((:checkInDate BETWEEN b.checkInDate AND b.checkOutDate) " +
            "OR (:checkOutDate BETWEEN b.checkInDate AND b.checkOutDate) " +
            "OR (b.checkInDate BETWEEN :checkInDate AND :checkOutDate) " +
            "OR (b.checkOutDate BETWEEN :checkInDate AND :checkOutDate)) ")
    List<Booking> findBookingConflicts(@Param("roomId") Long roomId,
                                      @Param("checkInDate") LocalDate checkInDate,
                                      @Param("checkOutDate") LocalDate checkOutDate,
                                      @Param("currentBookingId") Long currentBookingId);

    @Query("SELECT b FROM Booking b WHERE b.bookingDate = :date")
    List<Booking> findAllBookingsByDate(LocalDate date);
}
