package ru.practicum.shareit.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public Collection<UserDto> getAllUsers() {
        log.info("Получаем список всех пользователей из хранилища");
        return userRepository.getAllUsers()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto addNewUser(User user) {
        log.info("Добавляем нового пользователя в хранилище");
        return UserMapper.toUserDto(userRepository.addNewUser(user));
    }

    public UserDto updateUser(User updatedUser, Integer userId) {
        log.info("Обновляем пользователя в хранилище");
        return UserMapper.toUserDto(userRepository.updateUser(updatedUser, userId));
    }

    public UserDto getUserById(Integer userId) {
        log.info("Получаем пользователя с ID {}", userId);
        User user = userRepository.findUserById(userId);
        return UserMapper.toUserDto(user);
    }

    public void deleteUserById(Integer userId) {
        log.info("Удаление пользователя с ID {}", userId);
        userRepository.deleteUserById(userId);
        log.info("Пользователь с ID {} успешно удален", userId);
    }
}
