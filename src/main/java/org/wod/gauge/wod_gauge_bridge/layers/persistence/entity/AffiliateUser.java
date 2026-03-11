package org.wod.gauge.wod_gauge_bridge.layers.persistence.entity;

import jakarta.persistence.*;

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
