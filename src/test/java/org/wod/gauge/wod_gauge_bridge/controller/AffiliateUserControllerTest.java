package org.wod.gauge.wod_gauge_bridge.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateAffiliateUserRequest;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.Affiliate;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.UserDetails;
import org.wod.gauge.wod_gauge_bridge.service.AffiliateService;
import org.wod.gauge.wod_gauge_bridge.util.exception.AffiliateNotFoundException;
import org.wod.gauge.wod_gauge_bridge.util.exception.UserNotFoundException;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AffiliateUserController.class)
class AffiliateUserControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    AffiliateService affiliateService;

    @Test
    void addUserToAffiliate_valid_returns201() throws Exception {
        final CreateAffiliateUserRequest req = new CreateAffiliateUserRequest(1L, 1L);

        mvc.perform(post("/api/affiliate-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }

    @Test
    void addUserToAffiliate_invalid_affiliateId_returns400() throws Exception {
        final CreateAffiliateUserRequest req = new CreateAffiliateUserRequest(1L, 1L);

        doThrow(new AffiliateNotFoundException(req.getAffiliateId(), Affiliate.class))
                .when(affiliateService).addUserToAffiliate(req);

        mvc.perform(post("/api/affiliate-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Affiliate with ID 1 was not found."));
    }

    @Test
    void addUserToAffiliate_invalid_userDetailsId_returns400() throws Exception {
        final CreateAffiliateUserRequest req = new CreateAffiliateUserRequest(1L, 1L);

        doThrow(new UserNotFoundException(req.getUserDetailsId(), UserDetails.class))
                .when(affiliateService).addUserToAffiliate(req);

        mvc.perform(post("/api/affiliate-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("UserDetails with ID 1 was not found."));
    }
}