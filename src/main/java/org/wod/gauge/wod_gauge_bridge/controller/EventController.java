package org.wod.gauge.wod_gauge_bridge.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateEventRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.EventResponse;
import org.wod.gauge.wod_gauge_bridge.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(final EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(final @Valid @RequestBody CreateEventRequest request) {
        final EventResponse createdEvent = eventService.createEvent(request);
        return ResponseEntity.status(201).body(createdEvent);
    }
}
