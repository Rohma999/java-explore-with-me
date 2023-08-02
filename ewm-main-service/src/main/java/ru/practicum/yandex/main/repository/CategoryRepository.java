package ru.practicum.yandex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.yandex.main.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}