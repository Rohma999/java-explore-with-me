package ru.practicum.yandex.main.compilations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.yandex.main.compilations.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long>, JpaSpecificationExecutor<Compilation>,
        QuerydslPredicateExecutor<Compilation> {
}