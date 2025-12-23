package com.application.hotelmanagement.service;

import com.application.hotelmanagement.dto.RoomDto;
import com.application.hotelmanagement.exception.RoomNotFoundException;
import com.application.hotelmanagement.mapper.RoomMapper;
import com.application.hotelmanagement.model.BookingStatus;
import com.application.hotelmanagement.model.Hotel;
import com.application.hotelmanagement.model.Room;
import com.application.hotelmanagement.repo.RoomRepository;
import com.application.hotelmanagement.response.RoomResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final HotelService hotelService;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public String createRoom(RoomDto roomDto, Long hotelId) {
        log.info("Attempting to add room {} to hotelId {}", roomDto.getRoomNumber(), hotelId);
        Hotel hotel = hotelService.findHotelById(hotelId);
        boolean exists = roomRepository.findByRoomNumberAndHotel_HotelId(roomDto.getRoomNumber(), hotelId)
                .isPresent();

        if (exists) {
            throw new IllegalArgumentException("Room number " + roomDto.getRoomNumber() + " already exists in hotel " +
                    hotelId);
        } else {
            Room room = RoomMapper.fromDtoToEntity(roomDto);
            room.setHotel(hotel);
            Room savedRoom = roomRepository.save(room);
            log.info("Created room: roomId={}, roomNumber={}", savedRoom.getRoomId(), savedRoom.getRoomNumber());
            return savedRoom.getRoomType() + " room created successfully with room number: " + savedRoom.getRoomNumber();
        }
    }

    @Override
    public RoomResponse getRoom(Long hotelId, Integer roomNumber) {
        log.info("Searching for room with room number: {}", roomNumber);
        Room room = roomRepository.findByRoomNumberAndHotel_HotelId(roomNumber, hotelId)
                .orElseThrow(() -> new RoomNotFoundException("Room number: " + roomNumber +
                        "not found for hotelId: " + hotelId));
        log.info("Found room with room number: {}", room.getRoomNumber());
        return RoomMapper.fromEntityToResponse(room);
    }

    @Override
    public RoomResponse getRoomById(Long hotelId, Long roomId) {
        log.info("Fetching room details by room Id: {}", roomId);
        Room room = roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found for room Id" + roomId));
        return RoomMapper.fromEntityToResponse(room);
    }

    @Override
    public List<RoomResponse> getAllRooms(Long hotelId) {
        log.info("Fetching all rooms for hotelId: {}", hotelId);
        Optional<List<Room>> rooms = roomRepository.findAllRoomsByHotel_HotelId(hotelId);
        return rooms.orElseThrow(() -> new RuntimeException("Rooms not found for hotelId = " + hotelId))
                .stream()
                .map(RoomMapper::fromEntityToResponse)
                .toList();
    }

    @Override
    public List<RoomResponse> getAvailableRooms(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate) {
        log.info("Searching available rooms in hotel Id: {} between {} and {}",
                hotelId, checkInDate, checkOutDate);
        List<Room> availableRooms = roomRepository.findAvailableRooms(hotelId, checkInDate, checkOutDate);
        return availableRooms.stream().map(RoomMapper::fromEntityToResponse).toList();
    }

    @Override
    @Transactional
    public String updateRoom(RoomDto roomDto, Long hotelId, Integer roomNumber) {
        Room existingRoom = roomRepository.findByRoomNumberAndHotel_HotelId(roomNumber, hotelId)
                .orElseThrow(() -> new RoomNotFoundException("Room number: " + roomNumber + "not found for hotelId: "
                        + hotelId));
        existingRoom.setRoomType(roomDto.getRoomType());
        existingRoom.setPrice(roomDto.getPrice());
        Room updatedRoom = roomRepository.save(existingRoom);
        log.info("Updated room with room number: {} for hotelId: {}", roomNumber, hotelId);
        return updatedRoom.getRoomType() + " room successfully updated with room number: " + updatedRoom.getRoomNumber();
    }

    @Override
    @Transactional
    public String deleteRoom(Long hotelId, Integer roomNumber) {
        Room existingRoom = roomRepository.findByRoomNumberAndHotel_HotelId(roomNumber, hotelId)
                .orElseThrow(() -> new RoomNotFoundException("Room number: " + roomNumber + "not found for hotelId: "
                        + hotelId));
        roomRepository.delete(existingRoom);
        log.info("Deleted room with room number: {} from hotelId: {}", roomNumber, hotelId);
        return existingRoom.getRoomNumber() + " room successfully deleted";
    }

    /**
     * Do not delete this method as it is called in createNewBooking method in Booking Service
     * If found returns a room which is associated with hotel
     */
    @Override
    public Room findRoom(Long roomId) {
        return roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found for room Id" + roomId));
    }

    // Below methods are called in booking microservice.
    @Override
    public boolean isRoomExistsAndAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        log.info("Checking for room existence and its availability");
        Room room = roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found for room Id" + roomId));
        boolean hasOverlap = room.getBookings()
                .stream()
                .filter(b -> b.getStatus().equals(BookingStatus.CONFIRMED) ||
                        b.getStatus().equals(BookingStatus.CHECKED_IN))
                .anyMatch(b -> b.getCheckInDate().isBefore(checkOut) &&
                        b.getCheckOutDate().isAfter(checkIn));
        log.info("Room availability is: {}", !hasOverlap);
        return !hasOverlap;
    }

    @Override
    public RoomResponse getByRoomId(Long roomId) {
        Room room = roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found for room Id" + roomId));
        return RoomMapper.fromEntityToResponse(room);
    }

    @Override
    public Room updateRoomStatus(Room room) { return roomRepository.save(room); }
}
