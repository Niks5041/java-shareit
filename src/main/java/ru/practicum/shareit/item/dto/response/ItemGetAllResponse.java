package ru.practicum.shareit.item.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItemGetAllResponse {
    private Integer id;
    private String name;
    private String description;
    private Boolean available;
    private Integer ownerId;
    private Integer request;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
