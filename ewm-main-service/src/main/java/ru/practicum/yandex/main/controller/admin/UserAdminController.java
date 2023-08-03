package ru.practicum.yandex.main.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.yandex.main.dto.user.NewUserRequest;
import ru.practicum.yandex.main.dto.user.UserDto;
import ru.practicum.yandex.main.mapper.UserMapper;
import ru.practicum.yandex.main.model.User;
import ru.practicum.yandex.main.service.UserService;

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
public class UserAdminController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUser(@RequestBody @Valid NewUserRequest request) {
        log.info("POST /admin/users : {}", request);
        User user = service.addUser(mapper.toUser(request));
        UserDto response = mapper.toUserDto(user);
        log.info("POST /admin/users RESPONSE : {}", response);
        return response;
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
        List<UserDto> response = service.getAllUsers(ids, from, size).stream()
                .map(mapper::toUserDto)
                .collect(Collectors.toList());
        log.info("GET /admin/users?ids={} RESPONSE : {}", ids, response);
        return response;
    }

}