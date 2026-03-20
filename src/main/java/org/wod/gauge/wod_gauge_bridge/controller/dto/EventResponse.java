package org.wod.gauge.wod_gauge_bridge.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EventResponse {
    private Long eventId;
    private String title;
}
