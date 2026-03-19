package org.wod.gauge.wod_gauge_bridge.service;

import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateEventRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.EventResponse;

public interface EventService {
    EventResponse createEvent(final CreateEventRequest request);
}
