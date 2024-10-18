package ru.practicum.shareit.request.dto.request;

import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class InfoOfRequestByIdDto {
    private Integer id;
    private String description;
    private LocalDateTime created;
    private Collection<Item> items;
}
