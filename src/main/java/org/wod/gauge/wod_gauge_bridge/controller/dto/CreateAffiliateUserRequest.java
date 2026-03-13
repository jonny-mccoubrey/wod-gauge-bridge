package org.wod.gauge.wod_gauge_bridge.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAffiliateUserRequest {
    @NotNull(message = "Affiliate ID is required")
    private final Long affiliateId;

    @NotNull(message = "User Details ID is required")
    private final Long userDetailsId;
}
