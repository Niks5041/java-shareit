package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.request.UserCreateRequest;
import ru.practicum.shareit.user.dto.response.UserResponse;
import ru.practicum.shareit.user.dto.request.UserUpdateRequest;

import java.util.Collection;

public interface UserService {

    Collection<UserResponse> getAllUsers();

    UserResponse getUserById(Integer id);

    UserResponse addNewUser(UserCreateRequest userCreateRequest);

    UserResponse updateUser(UserUpdateRequest userUpdateRequest, Integer userId);

    void deleteUserById(Integer userId);
}
