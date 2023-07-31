package ru.practicum.yandex.main.users.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.yandex.main.users.UserMapper;
import ru.practicum.yandex.main.users.dto.NewUserRequest;
import ru.practicum.yandex.main.users.dto.UserDto;
import ru.practicum.yandex.main.users.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        log.info("POST /admin/users : {}", newUserRequest);
        return mapper.toUserDto(service.addUser(mapper.toUser(newUserRequest)));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long userId) {
        log.info("DELETE /admin/users/{}", userId);
        service.deleteUser(userId);
    }

    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(required = false) List<Long> ids,
                                     @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                     @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /admin/users?ids={},from={},size={}", ids, from, size);
        return service.getAllUsers(ids, from, size).stream()
                .map(mapper::toUserDto)
                .collect(Collectors.toList());
    }

}