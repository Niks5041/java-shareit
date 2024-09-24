package ru.practicum.shareit.request.dto.request;

import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
public class RequestDto {
    private Integer id;
    private String description;
    private User requestor;
    private LocalDateTime created;
}
