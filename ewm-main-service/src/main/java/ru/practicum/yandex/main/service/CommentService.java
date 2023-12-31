package ru.practicum.yandex.main.service;


import ru.practicum.yandex.main.dto.comment.UpdateCommentRequest;
import ru.practicum.yandex.main.model.Comment;

import java.util.List;

public interface CommentService {

    Comment addComment(long userId, long eventId, Comment comment);

    Comment getCommentById(long commentId);

    Comment updateComment(long commentId, long userId, long eventId, UpdateCommentRequest request);

    void deleteComment(long commentId, long userId, long eventId);

    List<Comment> getAllCommentsByEventId(long eventId, Integer from, Integer size);

    List<Comment> getAllCommentsByAuthorId(long authorId, Integer from, Integer size);

    List<Comment> getCommentsByText(String text, Integer from, Integer size);

    List<Comment> getAllComments(Integer from, Integer size);

    void deleteCommentByAdmin(long commentId);
}