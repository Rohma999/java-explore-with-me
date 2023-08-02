package ru.practicum.yandex.stats.server.service;

import ru.practicum.yandex.stats.dto.ViewStats;
import ru.practicum.yandex.stats.server.model.EndpointHit;

import java.util.List;

public interface EndpointHitService {

    EndpointHit addEndpointHit(EndpointHit endpointHit);

    List<ViewStats> getStats(List<String> uris, String start, String end, boolean unique);
}