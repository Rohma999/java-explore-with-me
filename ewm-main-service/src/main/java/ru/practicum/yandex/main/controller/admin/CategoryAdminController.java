package ru.practicum.yandex.main.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.yandex.main.dto.caregory.CategoryDto;
import ru.practicum.yandex.main.dto.caregory.NewCategoryDto;
import ru.practicum.yandex.main.mapper.CategoryMapper;
import ru.practicum.yandex.main.model.Category;
import ru.practicum.yandex.main.service.CategoryService;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/admin/categories")
public class CategoryAdminController {

    private final CategoryService service;
    private final CategoryMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@RequestBody @Valid NewCategoryDto request) {
        log.info("POST /admin/categories :{}", request);
        Category category = service.addCategory(mapper.toCategory(request));
        CategoryDto response = mapper.toCategoryDto(category);
        log.info("POST /admin/categories RESPONSE : {}", response);
        return response;
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@RequestBody @Valid NewCategoryDto request,
                                      @PathVariable long catId) {
        log.info("PATCH /admin/categories/{} : {}", catId, request);
        Category category = service.updateCategory(mapper.toCategory(request), catId);
        CategoryDto response = mapper.toCategoryDto(category);
        log.info("PATCH /admin/categories/{} RESPONSE : {}", catId, response);
        return response;
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long catId) {
        log.info("DELETE /admin/categories/{}", catId);
        service.deleteCategory(catId);
    }
}
