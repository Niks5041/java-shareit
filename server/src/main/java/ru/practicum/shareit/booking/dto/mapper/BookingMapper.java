package ru.practicum.shareit.booking.dto.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.dto.request.AddNewBookingRequest;
import ru.practicum.shareit.booking.dto.response.BookingDto;
import ru.practicum.shareit.booking.dto.response.BookingResponseWithDate;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;


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

    public Booking toBooking(AddNewBookingRequest bookingRequest,User user, Item item, BookingStatus bs) {
        Booking booking = new Booking();
        booking.setId(bookingRequest.getId());
        booking.setStart(bookingRequest.getStart());
        booking.setEnd(bookingRequest.getEnd());
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStatus(bs);
        return booking;
    }
}
