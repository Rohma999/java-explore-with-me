package ru.practicum.yandex.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.yandex.main.model.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>, QuerydslPredicateExecutor<User> {
}