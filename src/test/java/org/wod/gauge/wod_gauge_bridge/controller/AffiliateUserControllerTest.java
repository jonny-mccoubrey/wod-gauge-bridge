package org.wod.gauge.wod_gauge_bridge.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateAffiliateUserRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.UserResponse;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.Affiliate;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.UserDetails;
import org.wod.gauge.wod_gauge_bridge.service.AffiliateService;
import org.wod.gauge.wod_gauge_bridge.util.exception.AffiliateNotFoundException;
import org.wod.gauge.wod_gauge_bridge.util.exception.UserNotFoundException;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    void getAffiliateMembers_valid_returns200WithResponse() throws Exception {
        final Long affiliateId = 1L;
        final String username = "new-user";
        final String email = "newuser@test.com";
        final String name = "New User";

        final List<UserResponse> members = new ArrayList<>();
        members.add(new UserResponse(1L, username, email, name));

        when(affiliateService.getAffiliateMembers(affiliateId)).thenReturn(members);

        mvc.perform(get("/api/affiliate-users")
                        .param("affiliateId", String.valueOf(affiliateId))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].userDetailsId").value(1))
                .andExpect(jsonPath("$[0].username").value(username))
                .andExpect(jsonPath("$[0].emailAddress").value(email))
                .andExpect(jsonPath("$[0].name").value(name));
    }

    @Test
    void getAffiliateMembers_invalid_missingParam_returns400WithErrors() throws Exception {
        mvc.perform(get("/api/affiliate-users"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation Failed"))
                .andExpect(jsonPath("$.errors.affiliateId").value("Required request parameter is missing"));
    }

    @Test
    void getAffiliateMembers_invalid_returns400() throws Exception {
        when(affiliateService.getAffiliateMembers(anyLong())).thenThrow(new AffiliateNotFoundException(1L, Affiliate.class));

        mvc.perform(get("/api/affiliate-users")
                        .param("affiliateId", String.valueOf(1L))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Affiliate with ID 1 was not found."));
    }
}
