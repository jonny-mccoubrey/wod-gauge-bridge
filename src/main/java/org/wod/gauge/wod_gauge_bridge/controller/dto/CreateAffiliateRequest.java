package org.wod.gauge.wod_gauge_bridge.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAffiliateRequest {
    @NotNull(message = "Owner ID is required")
    private final Long ownerId;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot be longer than {max} characters")
    private final String name;

    @NotBlank(message = "Country is required")
    @Size(max = 50, message = "Country cannot be longer than {max} characters")
    private final String country;
}
