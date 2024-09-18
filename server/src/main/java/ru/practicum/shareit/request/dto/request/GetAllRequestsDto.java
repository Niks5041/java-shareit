package ru.practicum.shareit.request.dto.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class GetAllRequestsDto {
    private Integer id;
    private String description;
    private LocalDateTime created;
    private Collection<RequestDto> items;
}
