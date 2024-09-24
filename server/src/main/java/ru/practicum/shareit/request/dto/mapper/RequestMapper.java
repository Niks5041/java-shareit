package ru.practicum.shareit.request.dto.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.request.dto.request.GetAllRequestsDto;
import ru.practicum.shareit.request.dto.request.RequestDto;
import ru.practicum.shareit.request.dto.request.InfoOfRequestByIdDto;
import ru.practicum.shareit.request.model.Request;

import java.time.LocalDateTime;
import java.util.ArrayList;

@UtilityClass
public class RequestMapper {
    public RequestDto toRequestDto(Request request) {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        requestDto.setDescription(request.getDescription());
        requestDto.setRequestor(request.getRequestor());
        requestDto.setCreated(LocalDateTime.now());
        return requestDto;
    }

    public InfoOfRequestByIdDto toInfoOfRequestByIdDto(Request request) {
        InfoOfRequestByIdDto requestDto = new InfoOfRequestByIdDto();
        requestDto.setId(request.getId());
        requestDto.setDescription(request.getDescription());
        requestDto.setCreated(LocalDateTime.now());
        requestDto.setItems(new ArrayList<>());
        return requestDto;
    }

    public GetAllRequestsDto toGetAllRequestsDto(Request request) {
        GetAllRequestsDto requestDto = new GetAllRequestsDto();
        requestDto.setId(request.getId());
        requestDto.setDescription(request.getDescription());
        requestDto.setCreated(LocalDateTime.now());
        requestDto.setItems(new ArrayList<>());
        return requestDto;
    }
}





