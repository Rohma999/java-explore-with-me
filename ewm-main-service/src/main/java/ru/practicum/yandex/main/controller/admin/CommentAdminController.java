package ru.practicum.yandex.main.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.yandex.main.service.CommentService;

@Validated
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CommentAdminController {

    private final CommentService service;

    @DeleteMapping("/admin/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long commentId) {
        service.deleteCommentByAdmin(commentId);
    }
}
