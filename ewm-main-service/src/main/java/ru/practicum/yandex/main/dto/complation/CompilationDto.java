package ru.practicum.yandex.main.dto.complation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.yandex.main.dto.event.EventShortDto;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    private long id;

    private Boolean pinned;

    private String title;

    private Set<EventShortDto> events;
}