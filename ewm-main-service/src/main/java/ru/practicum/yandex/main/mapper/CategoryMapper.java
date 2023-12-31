package ru.practicum.yandex.main.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.yandex.main.dto.caregory.NewCategoryDto;
import ru.practicum.yandex.main.dto.caregory.CategoryDto;
import ru.practicum.yandex.main.model.Category;

@Component
public class CategoryMapper {

    public CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category toCategory(NewCategoryDto newCategoryDto) {
        return Category.builder()
                .name(newCategoryDto.getName())
                .build();
    }
}