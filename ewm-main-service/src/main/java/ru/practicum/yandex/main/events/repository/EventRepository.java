package ru.practicum.yandex.main.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.yandex.main.events.model.Event;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event>,
        QuerydslPredicateExecutor<Event> {

    List<Event> findByInitiatorId(long id, Pageable pageable);

    Event findByIdAndInitiatorId(long id, long initiatorId);

    Set<Event> findByIdIn(Set<Long> ids);
}