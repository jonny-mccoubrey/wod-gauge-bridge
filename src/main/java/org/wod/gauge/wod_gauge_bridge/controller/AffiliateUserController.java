package org.wod.gauge.wod_gauge_bridge.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateAffiliateUserRequest;
import org.wod.gauge.wod_gauge_bridge.service.AffiliateService;

@RestController
@RequestMapping("/api/affiliate-users")
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
}
