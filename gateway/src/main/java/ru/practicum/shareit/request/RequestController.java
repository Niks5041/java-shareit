package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.RequestClient;
import ru.practicum.shareit.request.dto.RequestDto;

@RestController
@RequestMapping(path = "/requests")
@Slf4j
@RequiredArgsConstructor
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Object addNewRequest(@RequestBody RequestDto request,
                                    @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Получен POST /requests запрос на добавление нового запроса итема: {}", request);
        ResponseEntity<Object> response = requestClient.addNewRequest(request, userId);
        log.info("Отправлен ответ POST /requests: {}", response.getBody());
        return response;
    }

    @GetMapping
    public Object getAllRequestsByUserId(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Получен GET /requests запрос на получение всех запросов итемов");
        ResponseEntity<Object> response = requestClient.getAllRequestsByUserId(userId);
        log.info("Отправлен ответ GET /requests: {}", response.getBody());
        return response;
    }

    @GetMapping("/all")
    public Object getAllRequestsByOtherUsers() {
        log.info("Получен GET /requests/all запрос на получение всех запросов итемов");
        ResponseEntity<Object> response = requestClient.getAllRequestsByOtherUsers();
        log.info("Отправлен ответ GET /requests/all: {}", response.getBody());
        return response;
    }

    @GetMapping("/{requestId}")
    public Object getInfoOfRequestById(@PathVariable Integer requestId,
                                                     @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Получен GET /requests/{} запрос на просмотр информации запроса с ID {}", requestId, userId);
        ResponseEntity<Object> response = requestClient.getInfoOfRequestById(requestId, userId);
        log.info("Отправлен ответ GET /requests/{}: {}", requestId, response.getBody());
        return response;
    }
}
