package org.wod.gauge.wod_gauge_bridge.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(AffiliateUserDetailsId.class)
@Table(name = "affiliate_user")
public class AffiliateUser {
    @Id
    private Long affiliateId;

    @Id
    private Long userDetailsId;
}
