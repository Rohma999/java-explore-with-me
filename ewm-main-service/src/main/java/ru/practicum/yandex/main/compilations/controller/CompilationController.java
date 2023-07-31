package ru.practicum.yandex.main.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.yandex.main.compilations.CompilationMapper;
import ru.practicum.yandex.main.compilations.dto.CompilationDto;
import ru.practicum.yandex.main.compilations.dto.NewCompilationDto;
import ru.practicum.yandex.main.compilations.dto.UpdateCompilationRequest;
import ru.practicum.yandex.main.compilations.service.CompilationService;

import javax.validation.Valid;
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

    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("POST /admin/compilations :{}", newCompilationDto);
        return mapper.toCompilationDto(service.addCompilation(newCompilationDto));
    }

    @DeleteMapping("/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable long compId) {
        log.info("DELETE /admin/compilations/{}", compId);
        service.deleteCompilation(compId);
    }

    @PatchMapping("/admin/compilations/{compId}")
    public CompilationDto updateCompilation(@PathVariable long compId,
                                            @RequestBody @Valid UpdateCompilationRequest compilation) {
        log.info("PATCH /admin/compilations/{} : {}", compId, compilation);
        return mapper.toCompilationDto(service.updateCompilation(compId, compilation));
    }
}