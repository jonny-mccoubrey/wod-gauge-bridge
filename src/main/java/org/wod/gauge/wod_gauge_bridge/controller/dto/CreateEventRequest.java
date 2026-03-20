package org.wod.gauge.wod_gauge_bridge.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CreateEventRequest {
    @NotNull(message = "Affiliate ID is required")
    private final Long affiliateId;

    @NotBlank(message = "Title is required")
    @Size(max = 50, message = "Title cannot be longer than {max} characters")
    private final String title;
}
