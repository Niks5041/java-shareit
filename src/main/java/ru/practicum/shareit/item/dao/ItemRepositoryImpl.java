package ru.practicum.shareit.item.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {

    UserRepository userRepository;

    private int generatorId = 0;
    private final Map<Integer, Item> items = new HashMap<>();

    @Override
    public Collection<Item> getAllItems() {
        log.info("Получен список всех итемов {}", items.values());
        return items.values();
    }

    @Override
    public Item addNewItem(Item item) {
        item.setId(++generatorId);
        items.put(item.getId(), item);
        log.info("Итем успешно добавлен : {}", item);
        return item;
    }

    @Override
    public Item modifyItem(Item item, Integer itemId) {
        items.put(itemId, item);
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
        log.info("Итем с ID {} найден", id);
        return items.values().stream()
                .filter(item -> id.equals(item.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Итем с ID " + id + " не найден"));
    }
}
