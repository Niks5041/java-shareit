package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.request.GetAllRequestsDto;
import ru.practicum.shareit.request.dto.request.RequestDto;
import ru.practicum.shareit.request.dto.request.InfoOfRequestByIdDto;
import ru.practicum.shareit.request.model.Request;

import java.util.Collection;


public interface RequestService {
    RequestDto addNewRequest(Request request, Integer userId);

    Collection<GetAllRequestsDto> getAllRequestsByUserId(Integer userId);

    Collection<GetAllRequestsDto> getAllRequestsByOtherUsers();

    InfoOfRequestByIdDto getInfoOfRequestById(Integer requestId, Integer userId);
}
