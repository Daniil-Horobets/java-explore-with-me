package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatsDto;
import ru.practicum.ewm.service.EwmStatsServiceService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class EwmStatsServiceController {

    private final EwmStatsServiceService service;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public EndpointHitDto createEndpointHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        log.info("EwmStatsServiceController - POST: /hit endpointHitDto={}", endpointHitDto);
        return service.createEndpointHit(endpointHitDto);
    }

    @GetMapping("/stats")
    @Transactional
    public List<ViewStatsDto> getViewStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("EwmStatsServiceController - GET: /stats start={}, end={}, uris={}, unique={}",
                start, end, uris, unique);
        LocalDateTime startLDT = LocalDateTime.parse(start, FORMATTER);
        LocalDateTime endLDT = LocalDateTime.parse(end, FORMATTER);
        return service.getViewStats(startLDT, endLDT, uris, unique);
    }
}
