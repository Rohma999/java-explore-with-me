package ru.practicum.yandex.main.controller.admin;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.yandex.main.dto.complation.CompilationDto;
import ru.practicum.yandex.main.dto.complation.NewCompilationDto;
import ru.practicum.yandex.main.dto.complation.UpdateCompilationRequest;
import ru.practicum.yandex.main.mapper.CompilationMapper;
import ru.practicum.yandex.main.service.CompilationService;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class CompilationAdminController {

    private final CompilationService service;
    private final CompilationMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("POST /admin/compilations :{}", newCompilationDto);
        return mapper.toCompilationDto(service.addCompilation(newCompilationDto));
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable long compId) {
        log.info("DELETE /admin/compilations/{}", compId);
        service.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable long compId,
                                            @RequestBody @Valid UpdateCompilationRequest compilation) {
        log.info("PATCH /admin/compilations/{} : {}", compId, compilation);
        return mapper.toCompilationDto(service.updateCompilation(compId, compilation));
    }
}
