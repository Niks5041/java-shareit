package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.dto.request.UserCreateRequest;
import ru.practicum.shareit.user.dto.response.UserResponse;
import ru.practicum.shareit.user.dto.request.UserUpdateRequest;
import ru.practicum.shareit.user.dto.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public Collection<UserResponse> getAllUsers() {
        log.info("Получаем список всех пользователей из хранилища");
        return userRepository.getAllUsers()
                .stream()
                .map(UserMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse addNewUser(UserCreateRequest userCreateRequest) {
        log.info("Добавляем нового пользователя в хранилище");
        User user = UserMapper.toUser(userCreateRequest);
        return UserMapper.toUserResponse(userRepository.addNewUser(user));
    }

    public UserResponse updateUser(UserUpdateRequest userUpdateRequest, Integer userId) {
        log.info("Обновляем пользователя в хранилище");
        User user = UserMapper.toUserFromUpdate(userUpdateRequest);
        return UserMapper.toUserResponse(userRepository.updateUser(user, userId));
    }

    public UserResponse getUserById(Integer userId) {
        log.info("Получаем пользователя с ID {}", userId);
        User user = userRepository.findUserById(userId);
        return UserMapper.toUserResponse(user);
    }

    public void deleteUserById(Integer userId) {
        log.info("Удаление пользователя с ID {}", userId);
        userRepository.deleteUserById(userId);
        log.info("Пользователь с ID {} успешно удален", userId);
    }
}
