package ru.practicum.shareit.user.dto.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.user.dto.request.UserCreateRequest;
import ru.practicum.shareit.user.dto.response.UserResponse;
import ru.practicum.shareit.user.dto.request.UserUpdateRequest;
import ru.practicum.shareit.user.model.User;

@UtilityClass
public final class UserMapper {

    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public User toUser(UserCreateRequest user) {
        return new User(
                null,
                user.getName(),
                user.getEmail()
        );
    }

    public User toUserFromUpdate(UserUpdateRequest user) {
        return new User(
                null,
                user.getName(),
                user.getEmail()
        );
    }
}


