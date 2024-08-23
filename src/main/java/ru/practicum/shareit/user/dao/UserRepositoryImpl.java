package ru.practicum.shareit.user.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private int generatorId = 0;
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public Collection<User> getAllUsers() {
        log.info("Получен список всех пользователей{}", users.values());
        return users.values();
    }

    @Override
    public User addNewUser(User user) {
        for (User existingUser : users.values()) {
            if (existingUser.getEmail().equals(user.getEmail())) {
                throw new IllegalArgumentException("Пользователь с таким email уже существует");
            }
        }
        user.setId(++generatorId);
        users.put(user.getId(), user);
        log.info("Пользователь успешно добавлен : {}", user);
        return user;
    }

    @Override
    public User updateUser(User updatedUser, Integer userId) {
        User oldUser = users.get(userId);
        if (oldUser == null) {
            log.info("Пользователь с ID {} не найден!", userId);
            throw new NotFoundException("Пользователь с ID " + userId + " не найден!");
        }
        for (User existingUser : users.values()) {
            if (existingUser.getEmail().equals(updatedUser.getEmail())) {
                throw new IllegalArgumentException("Пользователь с таким email уже существует");
            }
        }
        if (updatedUser.getName() != null && !updatedUser.getName().equals(oldUser.getName())) {
            oldUser.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().equals(oldUser.getEmail())) {
            oldUser.setEmail(updatedUser.getEmail());
        }

        users.put(userId, oldUser);
        log.info("Информация о пользователе успешно обновлена: {}", oldUser);
        return oldUser;
    }

    @Override
    public User findUserById(Integer userId) {
        log.info("Поиск пользователя с ID {}",userId);
        log.info("Пользователь с ID {} найден",userId);
        return users.values().stream()
                .filter(user -> userId.equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден"));
    }

    @Override
    public void deleteUserById(Integer userId) {
        log.info("Удаление пользователя с ID {}",userId);
        users.remove(userId);
        log.info("Пользователь с ID {} успешно удален", userId);
    }
}
