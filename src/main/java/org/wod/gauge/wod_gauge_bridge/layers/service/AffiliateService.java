package org.wod.gauge.wod_gauge_bridge.layers.service;

import org.wod.gauge.wod_gauge_bridge.dto.AffiliateResponse;
import org.wod.gauge.wod_gauge_bridge.dto.CreateAffiliateRequest;

public interface AffiliateService {
    AffiliateResponse createAffiliate(final CreateAffiliateRequest request);
}
