package ru.practicum.yandex.main.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.yandex.main.categories.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}