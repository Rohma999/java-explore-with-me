package ru.practicum.yandex.main.users.service;

import ru.practicum.yandex.main.users.model.User;

import java.util.List;

public interface UserService {

    User addUser(User user);

    void deleteUser(long id);

    List<User> getAllUsers(List<Long> ids, Integer from, Integer size);
}