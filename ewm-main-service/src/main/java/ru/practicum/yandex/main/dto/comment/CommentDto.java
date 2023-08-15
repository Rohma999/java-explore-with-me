package ru.practicum.yandex.main.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.yandex.main.dto.event.EventShortDto;
import ru.practicum.yandex.main.dto.user.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;

    private UserShortDto author;

    private EventShortDto event;

    private String text;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime edited;
}