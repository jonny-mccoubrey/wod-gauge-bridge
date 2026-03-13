package org.wod.gauge.wod_gauge_bridge.persistence.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "affiliate_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AffiliateUser {

    @EmbeddedId
    private AffiliateUserDetailsId id;

    @MapsId("affiliateId")
    @ManyToOne
    @JoinColumn(name = "affiliate_id")
    private Affiliate affiliate;

    @MapsId("userDetailsId")
    @ManyToOne
    @JoinColumn(name = "user_details_id")
    private UserDetails userDetails;
}
