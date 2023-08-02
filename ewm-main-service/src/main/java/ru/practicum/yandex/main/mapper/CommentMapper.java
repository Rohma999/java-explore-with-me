package ru.practicum.yandex.main.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.yandex.main.dto.comment.CommentDto;
import ru.practicum.yandex.main.dto.comment.NewCommentDto;
import ru.practicum.yandex.main.model.Comment;

@Component
@AllArgsConstructor
public class CommentMapper {

    private final UserMapper userMapper;
    private final EventMapper eventMapper;

    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .author(userMapper.toUserShortDto(comment.getAuthor()))
                .event(eventMapper.toEventShortDto(comment.getEvent()))
                .text(comment.getText())
                .created(comment.getCreated())
                .edited(comment.getEdited())
                .build();
    }

    public Comment toComment(NewCommentDto newCommentDto) {
        return Comment.builder()
                .text(newCommentDto.getText())
                .build();
    }
}
