package org.wod.gauge.wod_gauge_bridge.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AffiliateUserDetailsId implements Serializable {
    @Column(name = "affiliate_id")
    private Long affiliateId;

    @Column(name = "user_details_id")
    private Long userDetailsId;
}
