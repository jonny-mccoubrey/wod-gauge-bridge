package org.wod.gauge.wod_gauge_bridge.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank(message = "Username is required")
    @Size(max = 30, message = "Username cannot be longer than {max} characters")
    private String username;

    @NotBlank(message = "Email Address is required")
    @Email(message = "Email Address must be valid")
    @Size(max = 50, message = "Email Address cannot be longer than {max} characters")
    private String emailAddress;

    @NotBlank(message = "Password is required")
    @Size(max = 30, message = "Password cannot be longer than {max} characters")
    private String password;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot be longer than {max} characters")
    private String name;
}
