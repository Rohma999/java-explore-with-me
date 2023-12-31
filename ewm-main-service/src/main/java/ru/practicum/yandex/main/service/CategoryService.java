package ru.practicum.yandex.main.service;

import ru.practicum.yandex.main.model.Category;

import java.util.List;

public interface CategoryService {

    Category addCategory(Category category);

    void deleteCategory(long id);

    Category getCategoryById(long id);

    Category updateCategory(Category category, long id);

    List<Category> getAllCategories(Integer from, Integer size);
}