package ru.practicum.yandex.main.service;

import ru.practicum.yandex.main.model.User;

import java.util.List;

public interface UserService {

    User addUser(User user);

    void deleteUser(long id);

    List<User> getAllUsers(List<Long> ids, Integer from, Integer size);
}