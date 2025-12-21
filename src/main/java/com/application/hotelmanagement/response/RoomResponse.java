package com.application.hotelmanagement.response;

import com.application.hotelmanagement.model.RoomStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomResponse {
    private Long hotelId;
    private String hotelName;
    private Long roomId;
    private String roomType;
    private Integer roomNumber;
    private Double price;
    private RoomStatus roomStatus;
}
