package org.wod.gauge.wod_gauge_bridge.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AffiliateResponse {
    private Long affiliateId;
    private String name;
}
