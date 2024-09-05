package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemService {
    Collection<ItemDto> getAllItems();

    ItemDto getInfoOfItemById(Integer id);

    ItemDto addNewItem(Item item, Integer ownerId);

    ItemDto modifyItem(Item item, Integer itemId, Integer userId);

    Collection<ItemDto> findItemByText(String text);
}
