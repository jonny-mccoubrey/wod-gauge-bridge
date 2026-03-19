package org.wod.gauge.wod_gauge_bridge.service;

import org.springframework.stereotype.Service;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateEventRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.EventResponse;

@Service
public class EventServiceImpl implements EventService {
    @Override
    public EventResponse createEvent(final CreateEventRequest request) {
        return null;
    }
}
