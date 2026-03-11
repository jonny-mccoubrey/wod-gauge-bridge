package org.wod.gauge.wod_gauge_bridge.layers.persistence.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AffiliateUserDetailsId implements Serializable {
    private Long affiliateId;
    private Long userDetailsId;

    public AffiliateUserDetailsId() {}

    public AffiliateUserDetailsId(final Long affiliateId, final Long userDetailsId) {
        this.affiliateId = affiliateId;
        this.userDetailsId = userDetailsId;
    }
}
