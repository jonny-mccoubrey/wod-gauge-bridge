package org.wod.gauge.wod_gauge_bridge.service;

import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateUserRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.UserResponse;

public interface UserService {
    UserResponse createUser(final CreateUserRequest request);
}
