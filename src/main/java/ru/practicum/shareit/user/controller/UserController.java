package ru.practicum.shareit.user.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */

@RestController
@RequestMapping(path = "/users")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<UserDto> getAllUsers() {
        log.info("Пришел GET /users запрос на получение всех юзеров");
        Collection<UserDto> users = userService.getAllUsers();
        log.info("Отправлен ответ GET /users все юзеры с телом: {}", users);
        return users;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addNewUser(@RequestBody @Valid User user) {
        log.info("Пришел POST /users запрос на добавление нового юзера с телом: {}", user);
        UserDto addedUser = userService.addNewUser(user);
        log.info("Отправлен ответ POST /users добавлен юзер с телом: {}", addedUser);
        return addedUser;
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@RequestBody User updatedUser, @PathVariable Integer userId) {
        log.info("Пришел PATCH/ users запрос на обновление юзера {} с ID: {}", updatedUser, userId);
        UserDto updated = userService.updateUser(updatedUser, userId);
        log.info("Отправлен ответ PATCH/ users обновленного юзера {} с ID: {}", updatedUser, userId);
        return updated;
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Integer userId) {
        log.info("Пришел GET /users/{} запрос поиска юзера по айди", userId);
        UserDto user = userService.getUserById(userId);
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
