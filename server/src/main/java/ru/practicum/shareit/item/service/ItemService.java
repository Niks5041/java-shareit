package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.AddNewItemRequest;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.response.ItemGetAllResponse;
import ru.practicum.shareit.item.dto.response.ItemGetByIdResponse;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemService {
    Collection<ItemGetAllResponse> getAllItems(Integer userId);

    ItemGetByIdResponse getInfoOfItemById(Integer id);

    ItemDto addNewItem(AddNewItemRequest item, Integer ownerId);

    ItemDto modifyItem(Item item, Integer itemId, Integer userId);

    Collection<ItemDto> findItemByText(String text);

    CommentDto addComment(Integer itemId, Integer userId, CommentCreateDto comment);
}
