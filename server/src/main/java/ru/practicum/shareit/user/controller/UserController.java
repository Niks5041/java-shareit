package ru.practicum.shareit.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreateRequest;
import ru.practicum.shareit.user.dto.UserUpdateRequest;
import ru.practicum.shareit.user.dto.response.UserResponse;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<UserResponse> getAllUsers() {
        log.info("Пришел GET /users запрос на получение всех юзеров");
        Collection<UserResponse> users = userService.getAllUsers();
        log.info("Отправлен ответ GET /users все юзеры с телом: {}", users);
        return users;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse addNewUser(@RequestBody UserCreateRequest userCreateRequest) {
        log.info("Пришел POST /users запрос на добавление нового юзера с телом: {}", userCreateRequest);
        UserResponse addedUser = userService.addNewUser(userCreateRequest);
        log.info("Отправлен ответ POST /users добавлен юзер с телом: {}", addedUser);
        return addedUser;
    }

    @PatchMapping("/{userId}")
    public UserResponse updateUser(@RequestBody UserUpdateRequest userUpdateRequest, @PathVariable Integer userId) {
        log.info("Пришел PATCH/ users запрос на обновление юзера {} с ID: {}", userUpdateRequest, userId);
        UserResponse updated = userService.updateUser(userUpdateRequest, userId);
        log.info("Отправлен ответ PATCH/ users обновленного юзера {} с ID: {}", userUpdateRequest, userId);
        return updated;
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Integer userId) {
        log.info("Пришел GET /users/{} запрос поиска юзера по айди", userId);
        UserResponse user = userService.getUserById(userId);
        log.info("Отправлен ответ GET /users/{}: {}", userId, user);
        return user;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        log.info("Пришел DELETE запрос /users/{}", userId);
        userService.deleteUserById(userId);
        log.info("Отправлен ответ DELETE /users/{}", userId);
    }
}
