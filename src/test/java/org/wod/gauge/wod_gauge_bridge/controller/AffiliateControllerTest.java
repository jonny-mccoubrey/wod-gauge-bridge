package org.wod.gauge.wod_gauge_bridge.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.wod.gauge.wod_gauge_bridge.controller.dto.AffiliateResponse;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateAffiliateRequest;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.UserDetails;
import org.wod.gauge.wod_gauge_bridge.service.AffiliateService;
import org.wod.gauge.wod_gauge_bridge.util.exception.GlobalExceptionHandler;
import org.wod.gauge.wod_gauge_bridge.util.exception.UserNotFoundException;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AffiliateController.class)
@Import(GlobalExceptionHandler.class)
class AffiliateControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    AffiliateService affiliateService;

    @Test
    void createAffiliate_valid_returns201WithResponse() throws Exception {
        final String name = "New Affiliate";
        final String country = "United Kingdom";

        final CreateAffiliateRequest req = new CreateAffiliateRequest(1L, name, country);

        final AffiliateResponse res = new AffiliateResponse(1L, name);

        when(affiliateService.createAffiliate(req)).thenReturn(res);

        mvc.perform(post("/api/affiliates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.affiliateId").value(1L))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void createAffiliate_invalid_returns400WithErrors() throws Exception {
        final CreateAffiliateRequest req = new CreateAffiliateRequest(null, "", "");

        mvc.perform(post("/api/affiliates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Failed"))
                .andExpect(jsonPath("$.errors.ownerId").value("Owner ID is required"))
                .andExpect(jsonPath("$.errors.name").value("Name is required"))
                .andExpect(jsonPath("$.errors.country").value("Country is required"));
    }

    @Test
    void createAffiliate_invalid_length_returns400WithErrors() throws Exception {
        final CreateAffiliateRequest req = new CreateAffiliateRequest(
                1L,
                "a".repeat(101),
                "a".repeat(51)
        );

        mvc.perform(post("/api/affiliates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Failed"))
                .andExpect(jsonPath("$.errors.name").value("Name cannot be longer than 100 characters"))
                .andExpect(jsonPath("$.errors.country").value("Country cannot be longer than 50 characters"));
    }

    // Mock service to return exception for invalid Owner ID
    @Test
    void createAffiliate_invalid_ownerId_returns400() throws Exception {
        final CreateAffiliateRequest req = new CreateAffiliateRequest(1L, "Test Name", "United Kingdom");

        when(affiliateService.createAffiliate(req)).thenThrow(new UserNotFoundException(req.getOwnerId(), UserDetails.class));

        mvc.perform(post("/api/affiliates")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("UserDetails with ID 1 was not found."));
    }
}
