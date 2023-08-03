package ru.practicum.yandex.main.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.yandex.main.dto.event.EventFullDto;
import ru.practicum.yandex.main.dto.event.EventShortDto;
import ru.practicum.yandex.main.dto.event.NewEventDto;
import ru.practicum.yandex.main.dto.event.UpdateEventRequest;
import ru.practicum.yandex.main.mapper.EventMapper;
import ru.practicum.yandex.main.model.Event;
import ru.practicum.yandex.main.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class EventController {

    private final EventService service;
    private final EventMapper mapper;

    @GetMapping("/events")
    public List<EventShortDto> getEventsByText(@RequestParam(required = false) String text,
                                               @RequestParam(required = false) List<Long> categories,
                                               @RequestParam(required = false) Boolean paid,
                                               @RequestParam(required = false)
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                               @RequestParam(required = false)
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                               @RequestParam(required = false, defaultValue = "false")
                                               Boolean onlyAvailable,
                                               @RequestParam(required = false) String sort,
                                               @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                               @Positive @RequestParam(defaultValue = "10") Integer size,
                                               HttpServletRequest httpServletRequest) {
        log.info("GET /events text={},paid={},onlyAvailable={},categories={},rangeStart={},rangeEnd={},sort={},from={},size={}",
                text, paid, onlyAvailable, categories, rangeStart, rangeEnd, sort, from, size);
        List<EventShortDto> response = service.findByText(text, categories, paid, rangeStart, rangeEnd,
                        sort, from, size, onlyAvailable, httpServletRequest)
                .stream()
                .map(mapper::toEventShortDto)
                .collect(Collectors.toList());
        log.info("GET /events?text={} RESPONSE : {}", text, response);
        return response;
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEventById(@PathVariable long id, HttpServletRequest httpServletRequest) {
        Event event = service.getEventByIdAndState(id, httpServletRequest);
        EventFullDto response = mapper.toEventFullDto(event);
        log.info("GET /events/{} RESPONSE : {}", id, response);
        return response;
    }

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> getEventsByInitiatorId(@PathVariable long userId,
                                                      @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                      @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /users/{}/events?from={}&size={}", userId, from, size);
        List<EventShortDto> response = service.getByInitiatorId(userId, from, size).stream()
                .map(mapper::toEventShortDto)
                .collect(Collectors.toList());
        log.info("GET /users/{}/events RESPONSE : {}", userId, response);
        return response;
    }

    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable long userId,
                                 @RequestBody @Valid NewEventDto request) {
        log.info("POST /users/{}/events : {}", userId, request);
        Event event = service.addEvent(userId, mapper.toEvent(request));
        EventFullDto response = mapper.toEventFullDto(event);
        log.info("POST /users/{}/events RESPONSE : {}", userId, response);
        return response;
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEventByIdAndInitiatorId(@PathVariable long userId,
                                                   @PathVariable long eventId) {
        log.info("GET /users/{}/events/{}", userId, eventId);
        Event event = service.getEventByIdAndInitiatorId(eventId, userId);
        EventFullDto response = mapper.toEventFullDto(event);
        log.info("GET /users/{}/events/{} RESPONSE : {}", userId, eventId, response);
        return response;
    }

    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto updateEventPrivate(@PathVariable long userId,
                                           @PathVariable long eventId,
                                           @RequestBody @Valid UpdateEventRequest request) {
        log.info("PATCH /users/{}/events/{}", userId, eventId);
        Event event = service.updateEventUser(userId, eventId, request);
        EventFullDto response = mapper.toEventFullDto(event);
        log.info("PATCH /users/{}/events/{} RESPONSE : {}", userId, eventId, response);
        return response;
    }
}