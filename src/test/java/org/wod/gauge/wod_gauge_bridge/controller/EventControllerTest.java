package org.wod.gauge.wod_gauge_bridge.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateEventRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.EventResponse;
import org.wod.gauge.wod_gauge_bridge.service.EventService;
import org.wod.gauge.wod_gauge_bridge.util.exception.GlobalExceptionHandler;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
@Import(GlobalExceptionHandler.class)
class EventControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    EventService eventService;

    @Test
    void createEvent_valid_returns201WithResponse() throws Exception {
        final Long affiliateId = 1L;
        final String title = "Test Event";

        final CreateEventRequest req = new CreateEventRequest(affiliateId, title);

        final EventResponse res = new EventResponse(1L, title);

        when(eventService.createEvent(req)).thenReturn(res);

        mvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(jsonPath("$.eventId").value(1L))
                .andExpect(jsonPath("$.title").value(title));
    }

    @Test
    void createEvent_invalid_returns400WithErrors() throws Exception {
        final CreateEventRequest req = new CreateEventRequest(null, "");

        mvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Failed"))
                .andExpect(jsonPath("$.errors.affiliateId").value("Affiliate ID is required"))
                .andExpect(jsonPath("$.errors.title").value("Title is required"));
    }

    @Test
    void createEvent_invalid_length_returns400WithErrors() throws Exception {
        final CreateEventRequest req = new CreateEventRequest(
                1L,
                "a".repeat(51)
        );

        mvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Failed"))
                .andExpect(jsonPath("$.errors.title").value("Title cannot be longer than 50 characters"));
    }
}
