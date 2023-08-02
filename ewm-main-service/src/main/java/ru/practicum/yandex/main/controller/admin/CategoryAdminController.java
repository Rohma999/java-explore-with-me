package ru.practicum.yandex.main.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.yandex.main.dto.caregory.CategoryDto;
import ru.practicum.yandex.main.dto.caregory.NewCategoryDto;
import ru.practicum.yandex.main.mapper.CategoryMapper;
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
    public CategoryDto addCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("POST /admin/categories :{}", newCategoryDto);
        return mapper.toCategoryDto(service.addCategory(mapper.toCategory(newCategoryDto)));
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@RequestBody @Valid NewCategoryDto newCategoryDto,
                                      @PathVariable long catId) {
        log.info("PATCH /admin/categories/{} : {}", catId, newCategoryDto);
        return mapper.toCategoryDto(service.updateCategory(mapper.toCategory(newCategoryDto), catId));
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable long catId) {
        log.info("DELETE /admin/categories/{}", catId);
        service.deleteCategory(catId);
    }
}
