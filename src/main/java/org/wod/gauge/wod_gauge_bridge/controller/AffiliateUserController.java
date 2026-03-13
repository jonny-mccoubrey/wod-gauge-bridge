package org.wod.gauge.wod_gauge_bridge.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateAffiliateUserRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.UserResponse;
import org.wod.gauge.wod_gauge_bridge.service.AffiliateService;

import java.util.List;

@RestController
@RequestMapping("/api/affiliate-users")
@Validated
public class AffiliateUserController {
    private final AffiliateService affiliateService;

    public AffiliateUserController(final AffiliateService affiliateService) {
        this.affiliateService = affiliateService;
    }

    @PostMapping
    public ResponseEntity<Void> addUserToAffiliate(final @Valid @RequestBody CreateAffiliateUserRequest request) {
        affiliateService.addUserToAffiliate(request);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAffiliateMembers(final @RequestParam @NotNull Long affiliateId) {
        final List<UserResponse> members = affiliateService.getAffiliateMembers(affiliateId);
        return ResponseEntity.status(200).body(members);
    }
}
