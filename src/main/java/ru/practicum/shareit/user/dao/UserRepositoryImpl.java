package ru.practicum.shareit.user.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.InternalServerException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private int generatorId = 0;
    private final Map<Integer, User> users = new HashMap<>();
    private final Set<String> emailUniqSet = new HashSet<>();

    @Override
    public Collection<User> getAllUsers() {
        Collection<User> allUsers = users.values();
        log.info("Получен список всех пользователей{}", allUsers);
        return allUsers;
    }

    @Override
    public User addNewUser(User user) {
        final String email = user.getEmail();

        if (emailUniqSet.contains(email)) {
            throw new InternalServerException("Email: " + email + " already exists");
        }

        user.setId(++generatorId);
        users.put(user.getId(), user);
        emailUniqSet.add(email);

        log.info("Пользователь успешно добавлен : {}", user);
        return user;
    }

    @Override
    public User updateUser(User updatedUser, Integer userId) {
        User oldUser = users.get(userId);
        final String email = updatedUser.getEmail();

        if (oldUser == null) {
            log.info("Пользователь с ID {} не найден!", userId);
            throw new NotFoundException("Пользователь с ID " + userId + " не найден!");
        }
        if (emailUniqSet.contains(email)) {
            throw new InternalServerException("Email: " + email + " already exists");
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
        User existingUser = users.values().stream()
                .filter(user -> userId.equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + userId + " не найден"));

        log.info("Пользователь с ID {} найден",userId);
        return existingUser;
    }

    @Override
    public void deleteUserById(Integer userId) {
        log.info("Удаление пользователя с ID {}",userId);
        users.remove(userId);
        log.info("Пользователь с ID {} успешно удален", userId);
    }
}
