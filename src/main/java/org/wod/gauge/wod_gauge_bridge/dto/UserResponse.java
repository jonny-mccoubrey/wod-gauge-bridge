package org.wod.gauge.wod_gauge_bridge.dto;

import lombok.Builder;

@Builder
public class UserResponse {
    private Long userDetailsId;
    private String username;
    private String emailAddress;
}
