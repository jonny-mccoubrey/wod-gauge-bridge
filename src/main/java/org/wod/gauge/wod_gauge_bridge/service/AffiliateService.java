package org.wod.gauge.wod_gauge_bridge.service;

import org.wod.gauge.wod_gauge_bridge.controller.dto.AffiliateResponse;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateAffiliateRequest;

public interface AffiliateService {
    AffiliateResponse createAffiliate(final CreateAffiliateRequest request);
}
