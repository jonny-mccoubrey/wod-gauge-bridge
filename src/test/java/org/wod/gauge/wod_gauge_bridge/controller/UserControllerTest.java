package org.wod.gauge.wod_gauge_bridge.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateUserRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.UserResponse;
import org.wod.gauge.wod_gauge_bridge.service.UserService;
import org.wod.gauge.wod_gauge_bridge.util.exception.GlobalExceptionHandler;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@Import(GlobalExceptionHandler.class)
class UserControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    UserService userService;

    @Test
    void registerUser_valid_returns201WithResponse() throws Exception {
        final String username = "new-user";
        final String email = "testemail@test.com";
        final String name = "New User";

        final CreateUserRequest req = new CreateUserRequest(username, email, "password", name);

        final UserResponse res = new UserResponse(1L, username, email, name);

        when(userService.createUser(req)).thenReturn(res);

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userDetailsId").value(1L))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.emailAddress").value(email));

    }

    @Test
    void registerUser_invalid_returns400WithErrors() throws Exception {
        final CreateUserRequest req = new CreateUserRequest("", "invalid", "", "");

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Failed"))
                .andExpect(jsonPath("$.errors.username").value("Username is required"))
                .andExpect(jsonPath("$.errors.emailAddress").value("Email Address must be valid"))
                .andExpect(jsonPath("$.errors.password").value("Password is required"))
                .andExpect(jsonPath("$.errors.name").value("Name is required"));
    }

    @Test
    void registerUser_invalid_length_returns400WithErrors() throws Exception {
        final CreateUserRequest req = new CreateUserRequest(
                "a".repeat(31),
                "",
                "a".repeat(31),
                "a".repeat(101)
        );

        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Failed"))
                .andExpect(jsonPath("$.errors.username").value("Username cannot be longer than 30 characters"))
                .andExpect(jsonPath("$.errors.password").value("Password cannot be longer than 30 characters"))
                .andExpect(jsonPath("$.errors.name").value("Name cannot be longer than 100 characters"));
    }
}
