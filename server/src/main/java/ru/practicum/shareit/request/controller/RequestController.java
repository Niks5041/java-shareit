package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.request.GetAllRequestsDto;
import ru.practicum.shareit.request.dto.request.RequestDto;
import ru.practicum.shareit.request.dto.request.InfoOfRequestByIdDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.service.RequestServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping(path = "/requests")
@Slf4j
@RequiredArgsConstructor
public class RequestController {

    private final RequestServiceImpl rsi;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestDto addNewRequest(@RequestBody Request r,
                                    @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришел POST /requests запрос на добавление нового запроса итема с телом: {}", r);
        RequestDto addedRequestDto = rsi.addNewRequest(r,userId);
        log.info("Отправлен ответ POST /requests добавлен итем с телом: {}", addedRequestDto);
        return addedRequestDto;
    }

    @GetMapping
    public Collection<GetAllRequestsDto> getAllRequestsByUserId(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришел GET /requests запрос на получение всех запросов итемов");
        Collection<GetAllRequestsDto> itemRequests = rsi.getAllRequestsByUserId(userId);
        log.info("Отправлен ответ GET /requests все запросы итемов с телом: {}", itemRequests);
        return itemRequests;
    }

    @GetMapping("/all")
    public Collection<GetAllRequestsDto> getAllRequestsByOtherUsers() {
        log.info("Пришел GET /requests/all запрос на получение всех запросов итемов");
        Collection<GetAllRequestsDto> itemRequests = rsi.getAllRequestsByOtherUsers();
        log.info("Отправлен ответ GET /request/all все запросы итемов с телом: {}", itemRequests);
        return itemRequests;
    }

    @GetMapping("/{requestId}")
    public InfoOfRequestByIdDto getInfoOfRequestById(@PathVariable Integer requestId,
                                           @RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Пришел GET /requests/{} запрос на просмотр информации запроса с ID Юзера {}", requestId, userId);
        InfoOfRequestByIdDto requestDto = rsi.getInfoOfRequestById(requestId, userId);
        log.info("Отправлен ответ GET /requests/{}: {}", requestId, requestDto);
        return requestDto;
    }
}
