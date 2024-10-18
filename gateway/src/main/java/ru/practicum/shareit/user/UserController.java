package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreateRequest;
import ru.practicum.shareit.user.dto.UserUpdateRequest;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserClient userClient;

    @GetMapping
    public Object getAllUsers() {
        log.info("Получен GET /users запрос на получение всех пользователей");
        ResponseEntity<Object> response = userClient.getAllUsers();
        log.info("Отправлен ответ GET /users: {}", response.getBody());
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Object addNewUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        log.info("Получен POST /users запрос на добавление нового пользователя: {}", userCreateRequest);

        ResponseEntity<Object> response = userClient.addNewUser(userCreateRequest);
        log.info("Отправлен ответ POST /users: {}", response.getBody());
        return response;
    }

    @PatchMapping("/{userId}")
    public Object updateUser(@RequestBody @Valid UserUpdateRequest userUpdateRequest, @PathVariable Integer userId) {
        log.info("Получен PATCH /users запрос на обновление пользователя с ID {}: {}", userId, userUpdateRequest);
        ResponseEntity<Object> response = userClient.updateUser(userId, userUpdateRequest);
        log.info("Отправлен ответ PATCH /users: {}", response.getBody());
        return response;
    }

    @GetMapping("/{userId}")
    public Object getUserById(@PathVariable Integer userId) {
        log.info("Получен GET /users/{} запрос на получение пользователя по ID", userId);
        ResponseEntity<Object> response = userClient.getUserById(userId);
        log.info("Отправлен ответ GET /users/{}: {}", userId, response.getBody());
        return response;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        log.info("Получен DELETE /users/{} запрос на удаление пользователя", userId);
        userClient.deleteUserById(userId);
        log.info("Пользователь с ID {} успешно удален", userId);
    }
}
