package org.wod.gauge.wod_gauge_bridge.controller.dto;

import lombok.Builder;

@Builder
public class UserResponse {
    private Long userDetailsId;
    private String username;
    private String emailAddress;
}
