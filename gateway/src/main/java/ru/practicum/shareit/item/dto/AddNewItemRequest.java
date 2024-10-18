package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddNewItemRequest {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Boolean available;
    private Integer ownerId;
    private Integer requestId;
}
