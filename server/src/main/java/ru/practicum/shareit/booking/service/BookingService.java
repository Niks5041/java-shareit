package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.request.AddNewBookingRequest;
import ru.practicum.shareit.booking.dto.response.BookingDto;
import ru.practicum.shareit.booking.dto.request.BookingStatusDto;

import java.util.Collection;

public interface BookingService {

    BookingDto addNewBooking(AddNewBookingRequest booking, Integer userId);

    BookingDto confirmOrNotBooking(Integer bookingId, Boolean approved, Integer userId);

    BookingDto getInfoOfBookingById(Integer bookingId);

    Collection<BookingDto> getAllBookings(BookingStatusDto state, Integer userId);

    Collection<BookingDto> getBookingsForOwner(BookingStatusDto state, Integer userId);
}
