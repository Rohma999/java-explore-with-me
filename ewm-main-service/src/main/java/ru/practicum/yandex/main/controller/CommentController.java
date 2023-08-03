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
import ru.practicum.yandex.main.model.Comment;
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
                                 @RequestBody @Valid NewCommentDto request) {
        log.info("POST /users/{}/events/{}/comments : {}", userId, eventId, request);
        Comment comment = service.addComment(userId, eventId, mapper.toComment(request));
        CommentDto response = mapper.toCommentDto(comment);
        log.info("POST /users/{}/events/{}/comments RESPONSE : {}", userId, eventId, response);
        return response;
    }

    @GetMapping("/comments/{commentId}")
    public CommentDto getCommentById(@PathVariable long commentId) {
        log.info("GET /comments/{}", commentId);
        Comment comment = service.getCommentById(commentId);
        CommentDto response = mapper.toCommentDto(comment);
        log.info("GET /comments/{} RESPONSE : {}", commentId,response);
        return response;
    }

    @PatchMapping("/users/{userId}/events/{eventId}/comments/{commentId}")
    public CommentDto updateComment(@PathVariable long commentId,
                                    @PathVariable long userId,
                                    @PathVariable long eventId,
                                    @RequestBody @Valid UpdateCommentRequest request) {
        log.info("PATCH /users/{}/events/{}/comments/{} : {}", userId, eventId, commentId, request);
        Comment comment = service.updateComment(commentId, userId, eventId, request);
        CommentDto response = mapper.toCommentDto(comment);
        log.info("PATCH /users/{}/events/{}/comments/{} RESPONSE: {}", userId, eventId, commentId, response);
        return response;
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
        List<CommentDto> response = service.getAllCommentsByEventId(eventId, from, size).stream()
                .map(mapper::toCommentDto)
                .collect(Collectors.toList());
        log.info("GET /events/comments/{} RESPONSE : {}", eventId, response);
        return response;
    }

    @GetMapping("/users/comments/{userId}")
    public List<CommentDto> getAllCommentsByAuthorId(@PathVariable long userId,
                                                     @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                     @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /users/comments/{}?from={}&size={}", userId, from, size);
        List<CommentDto> response =  service.getAllCommentsByAuthorId(userId, from, size).stream()
                .map(mapper::toCommentDto)
                .collect(Collectors.toList());
        log.info("GET /users/comments/{} : {}", userId, response);
        return response;
    }

    @GetMapping("/comments")
    public List<CommentDto> getAllCommentsByText(@RequestParam String text,
                                                 @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /comments?from={}&size={} : {}", from, size, text);
        List<CommentDto> response = service.getCommentsByText(text, from, size).stream()
                .map(mapper::toCommentDto)
                .collect(Collectors.toList());
        log.info("GET /comments?text={} RESPONSE : {}", text,response);
        return response;
    }

    @GetMapping("/comments/all")
    public List<CommentDto> getAllComments(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                           @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /comments/all?from={}&size={}", from, size);
        List<CommentDto> response = service.getAllComments(from, size).stream()
                .map(mapper::toCommentDto)
                .collect(Collectors.toList());
        log.info("GET /comments/all RESPONSE : {}", response);
        return response;
    }
}
