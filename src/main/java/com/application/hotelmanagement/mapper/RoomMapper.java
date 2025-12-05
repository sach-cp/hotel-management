package com.application.hotelmanagement.mapper;

import com.application.hotelmanagement.dto.RoomDto;
import com.application.hotelmanagement.model.Room;
import com.application.hotelmanagement.response.RoomResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoomMapper {
    public static Room fromDtoToEntity(RoomDto roomDto) {
        return Room.builder()
                .roomType(roomDto.getRoomType())
                .roomNumber(roomDto.getRoomNumber())
                .price(roomDto.getPrice())
                .build();
    }

    public static RoomResponse fromEntityToResponse(Room room) {
        return RoomResponse.builder()
                .hotelId(room.getHotel().getHotelId())
                .hotelName(room.getHotel().getHotelName())
                .roomId(room.getRoomId())
                .roomType(room.getRoomType())
                .roomNumber(room.getRoomNumber())
                .price(room.getPrice())
                .build();
    }
}
