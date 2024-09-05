package ru.practicum.shareit.item.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {

    private int generatorId = 0;
    private final Map<Integer, Item> items = new HashMap<>();

    @Override
    public Collection<Item> getAllItems() {
        Collection<Item> allItems = items.values();
        log.info("Получен список всех итемов {}", allItems);
        return allItems;
    }

    @Override
    public Item addNewItem(Item item) {
        item.setId(++generatorId);
        items.put(item.getId(), item);
        log.info("Итем успешно добавлен : {}", item);
        return item;
    }

    @Override
    public Item modifyItem(Item item) {
        items.put(item.getId(), item);
        log.info("Информация об итеме успешно обновлена: {}", item);
        return item;
    }

    @Override
    public Collection<Item> findItemByText(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        String lowerCaseText = text.toLowerCase();

        return items.values().stream()
                    .filter(item -> item.getName().toLowerCase().contains(lowerCaseText) ||
                            item.getDescription().toLowerCase().contains(lowerCaseText))
                    .filter(Item::getAvailable)
                    .collect(Collectors.toList());
    }

    @Override
    public Item getInfoOfItemById(Integer id) {
        log.info("Поиск итема с ID {}", id);
        Item existItem = items.values().stream()
                .filter(item -> id.equals(item.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Итем с ID " + id + " не найден"));
        log.info("Итем с ID {} найден", id);
        return existItem;
    }
}
