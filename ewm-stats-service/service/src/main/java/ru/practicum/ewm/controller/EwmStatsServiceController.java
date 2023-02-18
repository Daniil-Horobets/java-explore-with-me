package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatsDto;
import ru.practicum.ewm.service.EwmStatsServiceService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class EwmStatsServiceController {

    @Autowired
    private final EwmStatsServiceService service;

    @PostMapping("/hit")
    public EndpointHitDto createEndpointHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        log.info("EwmStatsServiceController - POST: /hit endpointHitDto={}", endpointHitDto);
        return service.createEndpointHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getViewStats(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false) String uris,
            @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("EwmStatsServiceController - GET: /stats start={}, end={}, uris={}, unique={}",
                start, end, uris, unique);
        LocalDateTime startLDT = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endLDT = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<String> urisList = Arrays.stream(uris.substring(1, uris.length() - 1).split(", "))
                .map(String::trim)
                .collect(Collectors.toList());
        return service.getViewStats(startLDT, endLDT, urisList, unique);
    }
}