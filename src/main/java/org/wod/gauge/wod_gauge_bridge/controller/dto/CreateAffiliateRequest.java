package org.wod.gauge.wod_gauge_bridge.controller.dto;

import lombok.Data;

@Data
public class CreateAffiliateRequest {
    private final Long ownerId;
    private final String name;
    private final String country;
}
