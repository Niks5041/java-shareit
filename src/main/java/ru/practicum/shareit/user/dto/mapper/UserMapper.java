package ru.practicum.shareit.user.dto.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@UtilityClass
public final class UserMapper {
    public UserDto toUserDto(User user) {
        return  new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
