package org.wod.gauge.wod_gauge_bridge.persistence.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AffiliateUserDetailsId implements Serializable {
    private Long affiliateId;
    private Long userDetailsId;
}
