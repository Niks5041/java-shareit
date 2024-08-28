package ru.practicum.shareit.request.model;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;

@Data
public class ItemRequest {
    private Integer id;
    private String name;
    private String description;
    private User requestor;
    private LocalDate created;
}
