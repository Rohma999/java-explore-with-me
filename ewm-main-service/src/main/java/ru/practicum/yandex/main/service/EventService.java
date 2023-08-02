package ru.practicum.yandex.main.service;

import ru.practicum.yandex.main.dto.event.UpdateEventRequest;
import ru.practicum.yandex.main.model.Event;
import ru.practicum.yandex.main.model.EventState;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<Event> getAllEvents(List<Long> users, List<EventState> states, List<Long> categories, LocalDateTime rangeStart,
                             LocalDateTime rangeEnd, Integer from, Integer size);

    Event updateEventAdmin(long id, UpdateEventRequest event);

    List<Event> findByText(String text, List<Long> categories, Boolean paid, LocalDateTime start, LocalDateTime end,
                           String order, Integer from, Integer size, Boolean onlyAvailable,
                           HttpServletRequest httpServletRequest);

    Event getEventByIdAndState(long id, HttpServletRequest httpServletRequest);

    List<Event> getByInitiatorId(long id, Integer from, Integer size);

    Event addEvent(long id, Event event);

    Event getEventByIdAndInitiatorId(long eventId, long initiatorId);

    Event updateEventUser(long initiatorId, long eventId, UpdateEventRequest event);
}