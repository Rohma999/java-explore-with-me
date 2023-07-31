package ru.practicum.yandex.main.categories;

import ru.practicum.yandex.main.categories.model.Category;

import java.util.List;

public interface CategoryService {

    Category addCategory(Category category);

    void deleteCategory(long id);

    Category getCategoryById(long id);

    Category updateCategory(Category category, long id);

    List<Category> getAllCategories(Integer from, Integer size);
}