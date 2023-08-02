package ru.practicum.yandex.main.service;

import ru.practicum.yandex.main.dto.complation.NewCompilationDto;
import ru.practicum.yandex.main.dto.complation.UpdateCompilationRequest;
import ru.practicum.yandex.main.model.Compilation;

import java.util.List;

public interface CompilationService {

    List<Compilation> getAllByPinned(Boolean pinned, Integer from, Integer size);

    Compilation getCompilationById(long id);

    Compilation addCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(long id);

    Compilation updateCompilation(long id, UpdateCompilationRequest updateCompilationRequest);
}