package ru.practicum.shareit.item.dto.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.response.ItemGetAllResponse;
import ru.practicum.shareit.item.dto.response.ItemGetByIdResponse;
import ru.practicum.shareit.item.model.Item;

@UtilityClass
public final class ItemMapper {
    public ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwnerId(),
                item.getRequest() != null ? item.getRequest().getId() : null
        );
    }

    public static ItemGetByIdResponse toItemGetByIdResponse(Item item) {
        ItemGetByIdResponse response = new ItemGetByIdResponse();

        response.setId(item.getId());
        response.setName(item.getName());
        response.setDescription(item.getDescription());
        response.setAvailable(item.getAvailable());
        response.setOwnerId(item.getOwnerId());
        response.setRequest(item.getRequest() != null ? item.getRequest().getId() : null);

        return response;
    }

    public static ItemGetAllResponse toItemGetAllresponse(Item item) {
        ItemGetAllResponse response = new ItemGetAllResponse();

        response.setId(item.getId());
        response.setName(item.getName());
        response.setDescription(item.getDescription());
        response.setAvailable(item.getAvailable());
        response.setOwnerId(item.getOwnerId());
        response.setRequest(item.getRequest() != null ? item.getRequest().getId() : null);

        return response;
    }
}
