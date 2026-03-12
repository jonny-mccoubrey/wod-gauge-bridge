package org.wod.gauge.wod_gauge_bridge.layers.persistence.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "affiliate_user")
public class AffiliateUser {

    @EmbeddedId
    private AffiliateUserDetailsId id;

    @ManyToOne
    @MapsId("affiliateId")
    @JoinColumn(name = "affiliate_id")
    private Affiliate affiliate;

    @ManyToOne
    @MapsId("userDetailsId")
    @JoinColumn(name = "user_details_id")
    private UserDetails userDetails;
}
