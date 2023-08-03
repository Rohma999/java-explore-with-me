package ru.practicum.yandex.main.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.yandex.main.mapper.CategoryMapper;
import ru.practicum.yandex.main.model.Category;
import ru.practicum.yandex.main.service.CategoryService;
import ru.practicum.yandex.main.dto.caregory.CategoryDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService service;
    private final CategoryMapper mapper;

    @GetMapping("/categories/{catId}")
    public CategoryDto getCategoryById(@PathVariable long catId) {
        log.info("GET /categories/{}", catId);
        Category category = service.getCategoryById(catId);
        CategoryDto response = mapper.toCategoryDto(category);
        log.info("GET /categories/{} RESPONSE : {}", catId, response);
        return response;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories(@PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                              @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET /categories?from={}&size={}", from, size);
        List<CategoryDto> response = service.getAllCategories(from, size).stream()
                .map(mapper::toCategoryDto)
                .collect(Collectors.toList());
        log.info("GET /categories RESPONSE : {}", response);
        return response;
    }
}