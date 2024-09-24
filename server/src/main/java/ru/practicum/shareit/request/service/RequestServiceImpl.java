package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dao.RequestRepository;
import ru.practicum.shareit.request.dto.request.GetAllRequestsDto;
import ru.practicum.shareit.request.dto.request.RequestDto;
import ru.practicum.shareit.request.dto.mapper.RequestMapper;
import ru.practicum.shareit.request.dto.request.InfoOfRequestByIdDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;


import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository rr;
    private final UserRepository ur;
    private final ItemRepository ir;

    @Transactional
    @Override
    public RequestDto addNewRequest(Request request, Integer userId) {
        log.info("Добавляем новый запрос в хранилище");
        User user = ur.findById(userId)
                .orElseThrow(() -> new NotFoundException("Юзер не найден"));
        request.setRequestor(user);
        return RequestMapper.toRequestDto(rr.save(request));
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<GetAllRequestsDto> getAllRequestsByUserId(Integer userId) {
        log.info("Получаем все запросы из хранилища");
        return rr.findAll().stream()
                .filter(request -> request.getRequestor().getId().equals(userId))
                .map(RequestMapper::toGetAllRequestsDto)
                .sorted(Comparator.comparing(GetAllRequestsDto::getCreated).reversed())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<GetAllRequestsDto> getAllRequestsByOtherUsers() {
        log.info("Получаем все запросы юзеров из хранилища");
        return rr.findAll().stream()
                .map(RequestMapper::toGetAllRequestsDto)
                .sorted(Comparator.comparing(GetAllRequestsDto::getCreated).reversed())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public InfoOfRequestByIdDto getInfoOfRequestById(Integer requestId, Integer userId) {
        log.info("Получаем запрос с ID {}", requestId);

        Collection<Item> items = ir.findByRequestId(requestId);
        InfoOfRequestByIdDto requestByIdDto = rr.findById(requestId).map(RequestMapper::toInfoOfRequestByIdDto)
                .orElseThrow(() -> new NotFoundException("Запрос не найден"));
        requestByIdDto.setItems(items);

        return requestByIdDto;
    }
}
