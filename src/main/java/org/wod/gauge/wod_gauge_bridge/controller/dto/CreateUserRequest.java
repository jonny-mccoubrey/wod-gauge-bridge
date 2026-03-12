package org.wod.gauge.wod_gauge_bridge.controller.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    private String emailAddress;
    private String password;
    private String name;
}
