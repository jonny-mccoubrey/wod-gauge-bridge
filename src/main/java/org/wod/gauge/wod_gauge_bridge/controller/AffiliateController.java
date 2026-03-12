package org.wod.gauge.wod_gauge_bridge.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wod.gauge.wod_gauge_bridge.controller.dto.AffiliateResponse;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateAffiliateRequest;
import org.wod.gauge.wod_gauge_bridge.service.AffiliateService;

@RestController
@RequestMapping("/api/affiliates")
public class AffiliateController {
    private final AffiliateService affiliateService;

    public AffiliateController(final AffiliateService affiliateService) {
        this.affiliateService = affiliateService;
    }

    @PostMapping
    public ResponseEntity<AffiliateResponse> createAffiliate(final @RequestBody CreateAffiliateRequest request) {
        final AffiliateResponse createdAffiliate = affiliateService.createAffiliate(request);
        return ResponseEntity.ok(createdAffiliate);
    }
}
