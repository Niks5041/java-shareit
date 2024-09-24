package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.dto.UserCreateRequest;
import ru.practicum.shareit.user.dto.response.UserResponse;
import ru.practicum.shareit.user.dto.UserUpdateRequest;
import ru.practicum.shareit.user.dto.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)

    public Collection<UserResponse> getAllUsers() {
        log.info("Получаем список всех пользователей из хранилища");
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponse addNewUser(UserCreateRequest userCreateRequest) {
        log.info("Добавляем нового пользователя в хранилище");
        User user = UserMapper.toUser(userCreateRequest);
        return UserMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse updateUser(UserUpdateRequest userUpdateRequest, Integer userId) {
        log.info("Обновляем пользователя в хранилище");
        Optional<User> existingUserOpt = userRepository.findById(userId);
        if (existingUserOpt.isEmpty()) {
            throw new NotFoundException("Юзер не найден");
        }
        User existingUser = existingUserOpt.get();
        if (userUpdateRequest.getName() != null &&
                !userUpdateRequest.getName().equals(existingUser.getName())) {
            existingUser.setName(userUpdateRequest.getName());
        }
        if (userUpdateRequest.getEmail() != null &&
                !userUpdateRequest.getEmail().equals(existingUser.getEmail())) {
            existingUser.setEmail(userUpdateRequest.getEmail());
        }
        return UserMapper.toUserResponse(userRepository.save(existingUser));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Integer userId) {
        log.info("Получаем пользователя с ID {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Юзер не найден"));
        return UserMapper.toUserResponse(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Integer userId) {
        log.info("Удаление пользователя с ID {}", userId);
        userRepository.deleteById(userId);
        log.info("Пользователь с ID {} успешно удален", userId);
    }
}
