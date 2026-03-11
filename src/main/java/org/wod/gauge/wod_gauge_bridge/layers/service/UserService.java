package org.wod.gauge.wod_gauge_bridge.layers.service;

import org.wod.gauge.wod_gauge_bridge.dto.CreateUserRequest;
import org.wod.gauge.wod_gauge_bridge.dto.UserResponse;

public interface UserService {
    UserResponse createUser(final CreateUserRequest request);
}
