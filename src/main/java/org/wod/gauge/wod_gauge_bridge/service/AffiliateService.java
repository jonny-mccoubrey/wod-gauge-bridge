package org.wod.gauge.wod_gauge_bridge.service;

import org.wod.gauge.wod_gauge_bridge.controller.dto.AffiliateResponse;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateAffiliateRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateAffiliateUserRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.UserResponse;

import java.util.List;

public interface AffiliateService {
    AffiliateResponse createAffiliate(final CreateAffiliateRequest request);

    void addUserToAffiliate(final CreateAffiliateUserRequest request);

    List<UserResponse> getAffiliateMembers(final Long affiliateId);
}
