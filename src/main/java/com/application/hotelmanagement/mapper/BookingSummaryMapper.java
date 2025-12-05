package com.application.hotelmanagement.mapper;

import com.application.hotelmanagement.model.Booking;
import com.application.hotelmanagement.response.BookingSummaryResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookingSummaryMapper {

    public static BookingSummaryResponse fromEntityToResponse(Booking booking) {
        return BookingSummaryResponse.builder()
                .bookingId(booking.getBookingId())
                .hotelName(booking.getHotel().getHotelName())
                .firstName(booking.getCustomer().getFirstName())
                .lastName(booking.getCustomer().getLastName())
                .phoneNumber(booking.getCustomer().getPhoneNumber())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .build();
    }
}
