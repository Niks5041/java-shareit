package ru.practicum.shareit.booking.dto;

import java.util.Optional;

public enum BookingStatusDto {
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED;

    public static Optional<BookingStatusDto> from(String stringState) {
        for (BookingStatusDto state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}

