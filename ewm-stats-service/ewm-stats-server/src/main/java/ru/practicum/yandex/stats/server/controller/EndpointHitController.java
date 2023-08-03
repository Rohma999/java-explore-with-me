package ru.practicum.yandex.stats.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.yandex.stats.dto.ViewStats;
import ru.practicum.yandex.stats.server.exception.ValidationException;
import ru.practicum.yandex.stats.server.model.EndpointHit;
import ru.practicum.yandex.stats.server.service.EndpointHitService;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class EndpointHitController {

    public static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EndpointHitService service;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHit addEndpointHit(@RequestBody EndpointHit request) {
        log.info("POST /hit : {}", request);
        EndpointHit response = service.addEndpointHit(request);
        log.info("POST /hit RESPONSE : {}", response);
        return response;
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam @NotNull String start,
                                    @RequestParam @NotNull String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") boolean unique) {
        if (LocalDateTime.parse(start, DATE).isAfter(LocalDateTime.parse(end, DATE))) {
            throw new ValidationException("Start after end");
        }
        log.info("GET /stats?start={}&end={}&uris={}&unique={}", start, end, uris, unique);
        List<ViewStats> response = service.getStats(uris, start, end, unique);
        log.info("GET /stats RESPONSE : {}", response);
        return response;
    }
}