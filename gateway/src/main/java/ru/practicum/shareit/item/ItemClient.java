package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.AddNewItemRequest;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;


@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getAllItems(Integer userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> addNewItem(AddNewItemRequest item, Integer userId) {
        return post("", userId, item);
    }

    public ResponseEntity<Object> modifyItem(Integer itemId, ItemDto updatedItem, Integer userId) {
        return patch("/" + itemId, userId, updatedItem);
    }

    public ResponseEntity<Object> getInfoOfItemById(Integer itemId) {
        return get("/" + itemId);
    }

    public ResponseEntity<Object> addComment(Integer itemId, Integer userId, CommentCreateDto comment) {
        return post("/" + itemId + "/comment", userId, comment);
    }

    public ResponseEntity<Object> findItemByText(String text) {
        return get("/search?text=" + text);
    }
}
