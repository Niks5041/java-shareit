package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ru.practicum.shareit.booking.dto.AddNewBookingRequest;
import ru.practicum.shareit.booking.dto.BookingStatusDto;


@Slf4j
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {

	private final BookingClient bookingClient;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Object addNewBooking(@RequestBody @Valid AddNewBookingRequest booking,
									@RequestHeader("X-Sharer-User-Id") Integer userId) {
		log.info("Получен POST /bookings запрос на добавление нового бронирования: {} для юзера ID {}", booking, userId);
		ResponseEntity<Object> response = bookingClient.addNewBooking(booking, userId);
		log.info("Отправлен ответ POST /bookings: {}", response.getBody());
		return response;
	}

	@PatchMapping("/{bookingId}")
	public Object confirmOrNotBooking(@PathVariable Integer bookingId,
										  @RequestParam Boolean approved,
										  @RequestHeader("X-Sharer-User-Id") Integer userId) {
		log.info("Получен PATCH /bookings/{} запрос на подтверждение/отклонение бронирования: {}", bookingId, approved);
		ResponseEntity<Object> response = bookingClient.confirmOrNotBooking(bookingId, approved, userId);
		log.info("Отправлен ответ PATCH /bookings: {}", response.getBody());
		return response;
	}

	@GetMapping("/{bookingId}")
	public Object getInfoOfBookingById(@PathVariable Integer bookingId) {
		log.info("Получен GET /bookings/{} запрос на просмотр информации бронирования", bookingId);
		ResponseEntity<Object> response = bookingClient.getInfoOfBookingById(bookingId);
		log.info("Отправлен ответ GET /bookings/{}: {}", bookingId, response.getBody());
		return response;
	}

	@GetMapping
	public Object getAllBookings(
			@RequestParam(name = "state", defaultValue = "all") String stateParam,
			@RequestHeader("X-Sharer-User-Id") Integer userId) {
		log.info("Получен GET /bookings запрос на получение списка всех бронирований с состоянием: {}", stateParam);
		BookingStatusDto state = BookingStatusDto.from(stateParam)
				.orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));
		ResponseEntity<Object> response = bookingClient.getAllBookings(state, userId);
		log.info("Отправлен ответ GET /bookings: {}", response.getBody());
		return response;
	}

	@GetMapping("/owner")
	public Object getBookingsForOwner(
			@RequestParam(required = false, defaultValue = "ALL") BookingStatusDto state,
			@RequestHeader("X-Sharer-User-Id") int userId) {
		log.info("Получен GET /bookings/owner запрос на получение списка бронирований для всех вещей с состоянием: {}", state);
		ResponseEntity<Object> response = bookingClient.getBookingsForOwner(state, userId);
		log.info("Отправлен ответ GET /bookings/owner: {}", response.getBody());
		return response;
	}
}
