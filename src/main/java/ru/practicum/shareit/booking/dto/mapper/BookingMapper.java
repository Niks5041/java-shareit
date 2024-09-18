package ru.practicum.shareit.booking.dto.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.dto.request.AddNewBookingRequest;
import ru.practicum.shareit.booking.dto.response.BookingDto;
import ru.practicum.shareit.booking.dto.response.BookingResponseWithDate;
import ru.practicum.shareit.booking.model.Booking;


@UtilityClass
public class BookingMapper {
    public BookingDto toBookingDto(Booking booking) {
        return new BookingDto(
               booking.getId(),
               booking.getStart(),
               booking.getEnd(),
               booking.getItem(),
               booking.getBooker(),
               booking.getStatus()
        );
    }

    public BookingResponseWithDate toBookingWithDate(Booking booking) {
        return new BookingResponseWithDate(
                booking.getStart(),
                booking.getEnd()
        );
    }

    public Booking toBooking(AddNewBookingRequest bookingRequest) {
        return new Booking(
                bookingRequest.getId(),
                bookingRequest.getStart(),
                bookingRequest.getEnd(),
                null,
                bookingRequest.getBooker(),
                bookingRequest.getStatus()
        );
    }
}
