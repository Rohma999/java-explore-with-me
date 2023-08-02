package ru.practicum.yandex.main.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.yandex.main.dto.comment.CommentDto;
import ru.practicum.yandex.main.dto.comment.NewCommentDto;
import ru.practicum.yandex.main.dto.comment.UpdateCommentRequest;
import ru.practicum.yandex.main.mapper.CommentMapper;
import ru.practicum.yandex.main.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;
    private final CommentMapper mapper;

    @PostMapping("/users/{userId}/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@PathVariable long userId,
                                 @PathVariable long eventId,
                                 @RequestBody @Valid NewCommentDto comment) {
        log.info("POST /users/{}/events/{}/comments : {}", userId, eventId, comment);
        return mapper.toCommentDto(service.addComment(userId, eventId, mapper.toComment(comment)));
    }

    @GetMapping("/comments/{commentId}")
    public CommentDto getCommentById(@PathVariable long commentId) {
        log.info("GET /comments/{}", commentId);
        return mapper.toCommentDto(service.getCommentById(commentId));
    }

    @PatchMapping("/users/{userId}/events/{eventId}/comments/{commentId}")
    public CommentDto updateComment(@PathVariable long commentId,
                                    @PathVariable long userId,
                                    @PathVariable long eventId,
                                    @RequestBody @Valid UpdateCommentRequest request) {
        log.info("PATCH /users/{}/events/{}/comments/{} : {}", userId, eventId, commentId, request);
        return mapper.toCommentDto(service.updateComment(commentId, userId, eventId, request));
    }

    @DeleteMapping("/users/{userId}/events/{eventId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long commentId,
                              @PathVariable long userId,
                              @PathVariable long eventId) {
        log.info("DELETE /users/{}/events/{}/comments/{}", userId, eventId, commentId);
        service.deleteComment(commentId, userId, eventId);
    }

    @GetMapping("/events/comments/{eventId}")
    public List<CommentDto> getAllCommentsByEventId(@PathVariable long eventId,
                                                    @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                    @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /events/comments/{}?from={}&size={}", eventId, from, size);
        return service.getAllCommentsByEventId(eventId, from, size).stream()
                .map(mapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/comments/{userId}")
    public List<CommentDto> getAllCommentsByAuthorId(@PathVariable long userId,
                                                     @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                     @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /users/comments/{}?from={}&size={}", userId, from, size);
        return service.getAllCommentsByAuthorId(userId, from, size).stream()
                .map(mapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/comments")
    public List<CommentDto> getAllCommentsByText(@RequestParam String text,
                                                 @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /comments?from={}&size={} : {}", from, size, text);
        return service.getCommentsByText(text, from, size).stream()
                .map(mapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/comments/all")
    public List<CommentDto> getAllComments(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                           @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /comments/all?from={}&size={}", from, size);
        return service.getAllComments(from, size).stream()
                .map(mapper::toCommentDto)
                .collect(Collectors.toList());
    }
}
