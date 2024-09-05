package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserRepository {
    Collection<User> getAllUsers();

    User addNewUser(User user);

    User updateUser(User updatedUser, Integer userId);

    User findUserById(Integer userId);

    void deleteUserById(Integer userId);
}
