package ru.practicum.yandex.main.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.yandex.main.mapper.RequestMapper;
import ru.practicum.yandex.main.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.yandex.main.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.yandex.main.dto.request.ParticipationRequestDto;
import ru.practicum.yandex.main.service.RequestService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class RequestController {

    private final RequestService service;
    private final RequestMapper mapper;

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsByUserIdAndEventIdPrivate(@PathVariable long userId,
                                                                              @PathVariable long eventId) {
        log.info("GET /users/{}/events/{}/requests", userId, eventId);
        return service.findByRequesterIdAndEventId(userId, eventId).stream()
                .map(mapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequestsPrivate(@PathVariable long userId,
                                                                @PathVariable long eventId,
                                                                @RequestBody EventRequestStatusUpdateRequest request) {
        return service.updateRequests(userId, eventId, request);
    }

    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> getRequestsByUserId(@PathVariable long userId) {
        log.info("GET /users/{}/requests", userId);
        return service.findByRequesterId(userId).stream()
                .map(mapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addRequest(@PathVariable long userId,
                                              @RequestParam long eventId) {
        log.info("POST /users/{}/requests?eventId={}", userId, eventId);
        return mapper.toRequestDto(service.addRequest(userId, eventId));
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable long userId,
                                                 @PathVariable long requestId) {
        log.info("PATH /users/{}/requests/{}/cancel", userId, requestId);
        return mapper.toRequestDto(service.cancelRequest(requestId, userId));
    }
}