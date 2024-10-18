package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.request.AddNewBookingRequest;
import ru.practicum.shareit.booking.dto.response.BookingDto;
import ru.practicum.shareit.booking.dto.request.BookingStatusDto;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto addNewBooking(@RequestBody AddNewBookingRequest booking,
                                    @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришел POST /bookings запрос на добавление нового бронирования с телом и юзер ID {} {}", booking, userId);
        BookingDto bookingDto = bookingService.addNewBooking(booking, userId);
        log.info("Отправлен ответ POST /bookings бронирование добавлено с телом: {}", bookingDto);
        return bookingDto;
    }

    @PatchMapping("/{bookingId}")
    public BookingDto confirmOrNotBooking(@PathVariable Integer bookingId,
                                          @RequestParam Boolean approved,
                                          @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришел POST /bookings/{} запрос на потверждение или отклонение бронирования" +
                " с телом и параметром {}", bookingId, approved);
        BookingDto bookingDto = bookingService.confirmOrNotBooking(bookingId, approved, userId);
        if (bookingDto.getStatus().equals(BookingStatus.APPROVED)) {
            log.info("Отправлен ответ POST /bookings бронирование потверждено с телом: {}", bookingDto);
        } else if (bookingDto.getStatus().equals(BookingStatus.REJECTED)) {
            log.info("Отправлен ответ POST /bookings бронирование отклонено с телом: {}", bookingDto);
        }
        return bookingDto;
    }

    @GetMapping("/{bookingId}")
    public BookingDto getInfoOfBookingById(@PathVariable Integer bookingId) {
        log.info("Пришел GET /bookings/{} запрос на просмотр информации бронирования", bookingId);
        BookingDto bookingDto = bookingService.getInfoOfBookingById(bookingId);
        log.info("Отправлен ответ GET /bookings/{}: {}", bookingId, bookingDto);
        return bookingDto;
    }

    @GetMapping
    public Collection<BookingDto> getAllBookings(
            @RequestParam(required = false, defaultValue = "ALL") BookingStatusDto state,
            @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришел GET /bookings запрос на получение списка всех бронирований с состоянием: {}", state);
        Collection<BookingDto> bookings = bookingService.getAllBookings(state, userId);
        log.info("Отправлен ответ GET /bookings: {}", bookings);
        return bookings;
    }

    @GetMapping("/owner")
    public Collection<BookingDto> getBookingsForOwner(
            @RequestParam(required = false, defaultValue = "ALL") BookingStatusDto state,
            @RequestHeader("X-Sharer-User-Id") int userId) {
        log.info("Пришел GET /bookings/owner запрос на получение списка бронирований для всех вещей с состоянием: {}", state);
        Collection<BookingDto> bookings = bookingService.getBookingsForOwner(state, userId);
        log.info("Отправлен ответ GET /bookings/owner: {}", bookings);
        return bookings;
    }
}
