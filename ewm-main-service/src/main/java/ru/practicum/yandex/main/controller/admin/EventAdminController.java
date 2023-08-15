package ru.practicum.yandex.main.controller.admin;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.yandex.main.dto.event.EventFullDto;
import ru.practicum.yandex.main.dto.event.UpdateEventRequest;
import ru.practicum.yandex.main.mapper.EventMapper;
import ru.practicum.yandex.main.model.Event;
import ru.practicum.yandex.main.model.EventState;
import ru.practicum.yandex.main.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class EventAdminController {

    private final EventService service;
    private final EventMapper mapper;

    @GetMapping
    public List<EventFullDto> getAllEvents(@RequestParam(required = false) List<Long> users,
                                           @RequestParam(required = false) List<EventState> states,
                                           @RequestParam(required = false) List<Long> categories,
                                           @RequestParam(required = false)
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                           @RequestParam(required = false)
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                           @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                           @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /admin/events users={},states={},categories={},rangeStart={},rangeEnd={},from={},size={}",
                users, states, categories, rangeStart, rangeEnd, from, size);
        List<EventFullDto> response = service.getAllEvents(users, states, categories, rangeStart, rangeEnd, from, size)
                .stream()
                .map(mapper::toEventFullDto)
                .collect(Collectors.toList());
        log.info("GET /admin/events RESPONSE : {}", response);
        return response;
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventAdmin(@PathVariable long eventId,
                                         @RequestBody @Valid UpdateEventRequest request) {
        log.info("PATCH /admin/events/{} : {}", eventId, request);
        Event event = service.updateEventAdmin(eventId, request);
        EventFullDto response = mapper.toEventFullDto(event);
        log.info("PATCH /admin/events/{} RESPONSE : {}", eventId, response);
        return response;
    }
}
