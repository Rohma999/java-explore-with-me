package ru.practicum.yandex.main.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.yandex.main.dto.complation.NewCompilationDto;
import ru.practicum.yandex.main.dto.complation.UpdateCompilationRequest;
import ru.practicum.yandex.main.model.Compilation;
import ru.practicum.yandex.main.model.QCompilation;
import ru.practicum.yandex.main.repository.CompilationRepository;
import ru.practicum.yandex.main.repository.EventRepository;
import ru.practicum.yandex.main.exception.NotFoundException;
import ru.practicum.yandex.main.service.CompilationService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository repository;
    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Compilation> getAllByPinned(Boolean pinned, Integer from, Integer size) {
        QCompilation compilation = QCompilation.compilation;
        List<BooleanExpression> options = new ArrayList<>();

        if (pinned != null) {
            options.add(compilation.pinned.eq(pinned));
            return repository.findAll(options.get(0), PageRequest.of(from / size, size)).toList();
        }

        return repository.findAll(PageRequest.of(from / size, size)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Compilation getCompilationById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compilation not found"));
    }

    @Override
    public Compilation addCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = Compilation.builder()
                .pinned(newCompilationDto.isPinned())
                .title(newCompilationDto.getTitle())
                .build();
        if (newCompilationDto.getEvents() != null) {
            compilation.setEvents(eventRepository.findByIdIn(newCompilationDto.getEvents()));
        }

        return repository.save(compilation);
    }

    @Override
    public void deleteCompilation(long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException("Compilation not found");
        }
    }

    @Override
    public Compilation updateCompilation(long id, UpdateCompilationRequest compilation) {
        Compilation oldCompilation = getCompilationById(id);
        if (compilation.getPinned() != null) {
            oldCompilation.setPinned(compilation.getPinned());
        }
        if (compilation.getTitle() != null && !compilation.getTitle().isBlank()) {
            oldCompilation.setTitle(compilation.getTitle());
        }
        if (compilation.getEvents() != null) {
            oldCompilation.setEvents(eventRepository.findByIdIn(compilation.getEvents()));
        }
        return repository.save(oldCompilation);
    }
}