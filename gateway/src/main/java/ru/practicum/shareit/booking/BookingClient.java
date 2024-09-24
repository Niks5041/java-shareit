package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.AddNewBookingRequest;
import ru.practicum.shareit.booking.dto.BookingStatusDto;
import ru.practicum.shareit.client.BaseClient;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> addNewBooking(AddNewBookingRequest bookingRequest, Integer userId) {
        return post("", userId, bookingRequest);
    }

    public ResponseEntity<Object> confirmOrNotBooking(Integer bookingId, Boolean approved, Integer userId) {
        return patch("/" + bookingId + "?approved=" + approved, userId);
    }

    public ResponseEntity<Object> getInfoOfBookingById(Integer bookingId) {
        return get("/" + bookingId);
    }

    public ResponseEntity<Object> getAllBookings(BookingStatusDto state, Integer userId) {
        Map<String, Object> parameters = Map.of(
                "state", state.name()
        );
        return get("", Long.valueOf(userId), parameters);
    }

    public ResponseEntity<Object> getBookingsForOwner(BookingStatusDto state, Integer userId) {
        Map<String, Object> parameters = Map.of(
                "state", state.name()
        );
        return get("/owner", Long.valueOf(userId), parameters);
    }
}
