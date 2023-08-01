package ru.practicum.yandex.main.service;

import ru.practicum.yandex.main.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.yandex.main.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.yandex.main.model.Request;

import java.util.List;

public interface RequestService {

    List<Request> findByRequesterIdAndEventId(long requesterId, long eventId);

    EventRequestStatusUpdateResult updateRequests(long userId, long eventId, EventRequestStatusUpdateRequest request);

    List<Request> findByRequesterId(long requesterId);

    Request addRequest(long userId, long eventId);

    Request cancelRequest(long id, long requesterId);
}