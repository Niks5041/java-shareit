package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserService {

    Collection<UserDto> getAllUsers();

    UserDto getUserById(Integer id);

    UserDto addNewUser(User user);

    UserDto updateUser(User updatedUser, Integer userId);

    void deleteUserById(Integer userId);
}
