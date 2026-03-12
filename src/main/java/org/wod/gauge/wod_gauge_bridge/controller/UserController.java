package org.wod.gauge.wod_gauge_bridge.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateUserRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.UserResponse;
import org.wod.gauge.wod_gauge_bridge.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody CreateUserRequest request)
    {
        final UserResponse createdUser = userService.createUser(request);
        return ResponseEntity.status(201).body(createdUser);
    }
}
