package ru.practicum.shareit.booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingResponseWithDate {
    private LocalDateTime start;
    private LocalDateTime end;
}
