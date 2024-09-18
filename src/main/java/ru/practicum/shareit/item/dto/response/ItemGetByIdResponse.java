package ru.practicum.shareit.item.dto.response;

import lombok.Data;
import ru.practicum.shareit.booking.dto.response.BookingDto;
import ru.practicum.shareit.item.dto.CommentDto;

import java.util.List;

@Data
public class ItemGetByIdResponse {
    private Integer id;
    private String name;
    private String description;
    private Boolean available;
    private Integer ownerId;
    private Integer request;
    private BookingDto lastBooking;
    private BookingDto nextBooking;
    private List<CommentDto> comments;
}
