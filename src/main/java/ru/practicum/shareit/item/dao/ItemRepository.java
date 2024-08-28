package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemRepository {
    Collection<Item> getAllItems();

    Item getInfoOfItemById(Integer id);

    Item addNewItem(Item item);

    Item modifyItem(Item item);

    Collection<Item> findItemByText(String text);
}
