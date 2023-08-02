package ru.practicum.yandex.main.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.yandex.main.dto.complation.CompilationDto;
import ru.practicum.yandex.main.mapper.CompilationMapper;
import ru.practicum.yandex.main.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class CompilationController {

    private final CompilationService service;
    private final CompilationMapper mapper;

    @GetMapping("/compilations")
    public List<CompilationDto> getAllPinnedCompilation(@RequestParam(required = false) Boolean pinned,
                                                        @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                        @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /compilations?pinned={},from={},size={}", pinned, from, size);
        return service.getAllByPinned(pinned, from, size).stream()
                .map(mapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/compilations/{compId}")
    public CompilationDto getCompilationById(@PathVariable long compId) {
        log.info("GET /compilations/{}", compId);
        return mapper.toCompilationDto(service.getCompilationById(compId));
    }

}