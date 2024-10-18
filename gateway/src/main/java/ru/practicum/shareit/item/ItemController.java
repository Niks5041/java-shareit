package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.AddNewItemRequest;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collections;

@RestController
@RequestMapping("/items")
@Slf4j
@RequiredArgsConstructor
@Validated
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addComment(@PathVariable Integer itemId,
                                 @RequestHeader("X-Sharer-User-Id") Integer userId,
                                 @RequestBody @Valid CommentCreateDto comment) {
        log.info("Получен POST /items/{}/comment запрос на добавление комментария: {}", itemId, comment);
        ResponseEntity<Object> response = itemClient.addComment(itemId, userId, comment);
        log.info("Отправлен ответ POST /items/{}/comment: {}", itemId, response.getBody());
        return response;
    }

    @GetMapping
    public Object getAllItems(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Получен GET /items запрос на получение всех итемов");
        ResponseEntity<Object> response = itemClient.getAllItems(userId);
        log.info("Отправлен ответ GET /items: {}", response.getBody());
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Object addNewItem(@RequestBody @Valid AddNewItemRequest item,
                              @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Получен POST /items запрос на добавление нового итема: {}", item);
        ResponseEntity<Object> response = itemClient.addNewItem(item, userId);
        log.info("Отправлен ответ POST /items: {}", response.getBody());
        return response;
    }

    @PatchMapping("/{itemId}")
    public Object modifyItem(@RequestBody @Valid ItemDto updatedItem,
                              @PathVariable Integer itemId,
                              @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Получен PATCH /items запрос на обновление итема с ID {}: {}", itemId, updatedItem);
        ResponseEntity<Object> response = itemClient.modifyItem(itemId, updatedItem, userId);
        log.info("Отправлен ответ PATCH /items: {}", response.getBody());
        return response;
    }

    @GetMapping("/{itemId}")
    public Object getInfoOfItemById(@PathVariable Integer itemId) {
        log.info("Получен GET /items/{} запрос на получение информации об итеме", itemId);
        ResponseEntity<Object> response = itemClient.getInfoOfItemById(itemId);
        log.info("Отправлен ответ GET /items/{}: {}", itemId, response.getBody());
        return response;
    }

    @GetMapping("/search")
    public Object findItemByText(@RequestParam String text) {
        log.info("Получен GET /items/search запрос на поиск итема по тексту: {}", text);
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        ResponseEntity<Object> response = itemClient.findItemByText(text);
        log.info("Отправлен ответ GET /items/search: {}", response.getBody());
        return response;
    }
}
