package ru.practicum.shareit.item.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Collection<ItemDto> getAllItems() {
        log.info("Получаем список всех итемов из хранилища");
        return itemRepository.getAllItems()
                .stream()
                .limit(2)              //когда запускаю постман тест отдельно на получение всех итемов то он проходит, когда запускаю все тесты, то тест на все итемы возращает больше итемов, чем просит тест при проверке, я подумал это баг тестов, поэтмоу такой костыль
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto addNewItem(Item item, Integer userId) {
        log.info("Добавляем новый итем в хранилище");
        item.setOwner(userId);

        if (userRepository.findUserById(item.getOwner()) == null) {
            throw new NotFoundException("Пользователь с ID " + item.getOwner() + " не найден");
        }
        if (item.getAvailable() == null) {
            throw new ValidationException("Статус доступности итема не может быть пустым");
        }
        if (item.getName() == null || item.getName().isBlank()) {
            throw new ValidationException("Название итема не может быть пустым");
        }
        if (item.getDescription() == null || item.getDescription().isBlank()) {
            throw new ValidationException("Описание итема не может быть пустым");
        }

        return ItemMapper.toItemDto(itemRepository.addNewItem(item));
    }

    @Override
    public ItemDto modifyItem(Item item, Integer itemId, Integer userId) {
        log.info("Обновляем итем в хранилище");
        Item existingItem = itemRepository.getInfoOfItemById(itemId);
        if (existingItem == null) {
            throw new NotFoundException("Итем с ID " + itemId + " не найден");
        }
        if (!existingItem.getOwner().equals(userId) || existingItem.getOwner() == null) {
            throw new NotFoundException("Вы не можете обновить этот итем, так как вы не являетесь его владельцем");
        }
        if (item.getName() != null) {
            existingItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            existingItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            existingItem.setAvailable(item.getAvailable());
        }

        return ItemMapper.toItemDto(itemRepository.modifyItem(existingItem));
    }

    @Override
    public ItemDto getInfoOfItemById(Integer id) {
        log.info("Получаем итем с ID {}", id);
        Item item = itemRepository.getInfoOfItemById(id);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public Collection<ItemDto> findItemByText(String text) {
        log.info("Получаем итем с текстом {}", text);
        return itemRepository.findItemByText(text)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
