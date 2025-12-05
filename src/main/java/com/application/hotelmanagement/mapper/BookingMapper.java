package com.application.hotelmanagement.mapper;

import com.application.hotelmanagement.dto.BookingDto;
import com.application.hotelmanagement.model.Address;
import com.application.hotelmanagement.model.Booking;
import com.application.hotelmanagement.model.Customer;
import com.application.hotelmanagement.response.AddressResponse;
import com.application.hotelmanagement.response.BookingResponse;
import com.application.hotelmanagement.response.CustomerResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BookingMapper {

    public static Booking fromDtoToEntity(BookingDto bookingDto) {
        return Booking.builder()
                .checkInDate(bookingDto.getCheckInDate())
                .checkOutDate(bookingDto.getCheckOutDate())
                .numberOfPersons(bookingDto.getNumberOfPersons())
                .purposeOfVisit(bookingDto.getPurposeOfVisit())
                .totalAmount(bookingDto.getTotalAmount())
                .advanceAmount(bookingDto.getAdvanceAmount())
                .paymentMode(bookingDto.getPaymentMode())
                .paymentStatus(bookingDto.getPaymentStatus())
                .build();
    }

    public static BookingResponse fromEntityToResponse(Booking booking) {
        CustomerResponse customerResponse = convertCustomerEntityToResponse(booking.getCustomer());
        return BookingResponse.builder()
                .hotelName(booking.getHotel().getHotelName())
                .bookingId(booking.getBookingId())
                .roomType(booking.getRoom().getRoomType())
                .roomNumber(booking.getRoom().getRoomNumber())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .numberOfPersons(booking.getNumberOfPersons())
                .price(booking.getTotalAmount())
                .status(booking.getBookingStatus())
                .purposeOfVisit(booking.getPurposeOfVisit())
                .advanceAmount(booking.getAdvanceAmount())
                .paymentStatus(booking.getPaymentStatus())
                .customerResponse(customerResponse)
                .build();
    }

    public static CustomerResponse convertCustomerEntityToResponse(Customer customer) {
        AddressResponse addressResponse = convertAddressEntityToResponse(customer.getAddress());
        return CustomerResponse.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .addressResponse(addressResponse)
                .build();
    }

    private static AddressResponse convertAddressEntityToResponse(Address address) {
        return AddressResponse.builder()
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .pinCode(address.getPinCode())
                .addressDetails(address.getAddressDetails())
                .build();
    }
}
