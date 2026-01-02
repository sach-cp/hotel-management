package com.application.hotelmanagement.service;

import com.application.hotelmanagement.dto.RoomDto;
import com.application.hotelmanagement.exception.RoomAlreadyExistsException;
import com.application.hotelmanagement.exception.RoomNotFoundException;
import com.application.hotelmanagement.mapper.RoomMapper;
import com.application.hotelmanagement.model.BookingStatus;
import com.application.hotelmanagement.model.Hotel;
import com.application.hotelmanagement.model.Room;
import com.application.hotelmanagement.repo.RoomRepository;
import com.application.hotelmanagement.response.RoomResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final HotelService hotelService;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public RoomResponse createRoom(RoomDto roomDto, Long hotelId) {
        log.info("Attempting to add room {} to hotelId {}", roomDto.getRoomNumber(), hotelId);
        Hotel existingHotel = hotelService.findHotelById(hotelId);
        Room existingRoom = roomRepository.findByRoomNumberAndHotel_HotelId(roomDto.getRoomNumber(), hotelId);

        if (!ObjectUtils.isEmpty(existingRoom)) {
            throw new RoomAlreadyExistsException("Room number " + roomDto.getRoomNumber() +
                    " already exists in hotel " + hotelId);
        } else {
            Room room = RoomMapper.fromDtoToEntity(roomDto);
            room.setHotel(existingHotel);
            Room savedRoom = roomRepository.save(room);
            log.info("Successfully created new room: roomId={}, roomNumber={}",
                    savedRoom.getRoomId(), savedRoom.getRoomNumber());
            return RoomMapper.fromEntityToResponse(savedRoom);
        }
    }

    @Override
    public List<RoomResponse> getAllRooms(Long hotelId) {
        log.info("Fetching all rooms for hotelId: {}", hotelId);
        List<Room> rooms = roomRepository.findAllRoomsByHotel_HotelId(hotelId);
        if (CollectionUtils.isEmpty(rooms)) {
            log.info("No rooms found for hotelId: {}", hotelId);
            return Collections.emptyList();
        }
        return rooms.stream().map(RoomMapper::fromEntityToResponse).toList();
    }

    @Override
    @Transactional
    public RoomResponse updateRoom(RoomDto roomDto, Long hotelId, Long roomId) {
        Room existingRoom = roomRepository.findByRoomIdAndHotel_HotelId(roomId, hotelId);
        if (ObjectUtils.isEmpty(existingRoom)) {
            throw new RoomNotFoundException("Room with roomId: " + roomId + " not found for hotelId: " + hotelId);
        }
        existingRoom.setRoomType(roomDto.getRoomType());
        existingRoom.setPrice(roomDto.getPrice());
        Room updatedRoom = roomRepository.save(existingRoom);
        log.info("Updated room with roomId: {} for hotelId: {}", roomId, hotelId);
        return RoomMapper.fromEntityToResponse(updatedRoom);
    }

    @Override
    @Transactional
    public void deleteRoom(Long hotelId, Long roomId) {
        log.info("Retrieving room with room id: {} for hotelId: {} to delete", roomId, hotelId);
        Room existingRoom = roomRepository.findByRoomIdAndHotel_HotelId(roomId, hotelId);
        if (ObjectUtils.isEmpty(existingRoom)) {
            throw new RoomNotFoundException("Room with roomId: " + roomId + " not found for hotelId: " + hotelId);
        } else {
            log.info("Deleting room with room id: {} for hotelId: {}", roomId, hotelId);
            roomRepository.delete(existingRoom);
            log.info("Successfully deleted room with room id: {} for hotelId: {}", roomId, hotelId);
        }
    }

    /**
     * Do not delete this method as it is called in createNewBooking method in Booking Service
     * If found returns a room which is associated with hotel
     */
    @Override
    public Room findRoom(Long roomId) {
        return roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found for room Id " + roomId));
    }

    /**
     * Do not delete this method as it is called in createNewBooking method in Booking Service
     * If exists and available for booking returns a true value else a false value
     */
    @Override
    public Boolean isRoomExistsAndAvailable(Long roomId, LocalDate checkIn, LocalDate checkOut) {
        log.info("Checking for room existence and its availability");
        Room existingRoom = roomRepository.findByRoomId(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found for room Id " + roomId));
        boolean available = existingRoom.getBookings()
                .stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED
                        || b.getStatus() == BookingStatus.CHECKED_IN)
                .noneMatch(b -> b.getCheckInDate().isBefore(checkOut)
                        && b.getCheckOutDate().isAfter(checkIn));

        log.info("Room availability is: {}", available);
        return available;
    }

    /**
     * Do not delete this method as it is called in createNewBooking method in Booking Service
     * It updates the room status in the room entity while booking a room
     */
    // This method is called while booking a room only after checking its existence and availability
    @Override
    public Room updateRoomStatus(Room room) { return roomRepository.save(room); }
}
