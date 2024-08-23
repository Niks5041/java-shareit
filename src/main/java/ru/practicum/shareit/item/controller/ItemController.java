package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@AllArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public Collection<ItemDto> getAllItems() {
        log.info("Пришел GET /items запрос на получение всех итемов");
        Collection<ItemDto> items = itemService.getAllItems();
        log.info("Отправлен ответ GET /items все итемы с телом: {}", items);
        return items;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addNewItem(@RequestBody Item item, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришел POST /items запрос на добавление нового итема с телом: {}", item);
        ItemDto addedItem = itemService.addNewItem(item, userId);
        log.info("Отправлен ответ POST /items  добавлен итем с телом: {}", addedItem);
        return addedItem;
    }

    @PatchMapping("/{itemId}")
        public ItemDto modifyItem(@RequestBody Item updatedItem, @PathVariable Integer itemId, @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришел PUT /items запрос на обновление итема с телом: {}", updatedItem);
        ItemDto updatedItemDto = itemService.modifyItem(updatedItem, itemId, userId);
        log.info("Отправлен ответ PUT /items  обновленного итема с телом: {}", updatedItemDto);
        return updatedItemDto;
    }

    @GetMapping("/{itemId}")
    public ItemDto getInfoOfItemById(@PathVariable Integer itemId) {
        log.info("Пришел GET /items/{} запрос на просмотр информации итема", itemId);
        ItemDto itemDto = itemService.getInfoOfItemById(itemId);
        log.info("Отправлен ответ GET /items /{}: {}", itemId, itemDto);
        return itemDto;
    }

    @GetMapping("/search")
    public Collection<ItemDto> findItemByText(@RequestParam String text) {
        log.info("Пришел GET /items/search {} запрос на поиск итема по названию", text);
        Collection<ItemDto> itemsDto = itemService.findItemByText(text);
        log.info("Отправлен ответ GET /items/search /{}: {}", text, itemsDto);
        return itemsDto;
    }
}
