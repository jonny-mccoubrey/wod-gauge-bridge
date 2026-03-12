package org.wod.gauge.wod_gauge_bridge.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long userDetailsId;
    private String username;
    private String emailAddress;
}
