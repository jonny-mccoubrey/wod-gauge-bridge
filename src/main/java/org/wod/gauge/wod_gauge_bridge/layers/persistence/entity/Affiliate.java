package org.wod.gauge.wod_gauge_bridge.layers.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "affiliate")
public class Affiliate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "affiliate_id")
    private Long affiliateId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "owner_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_affiliate_owner")
    )
    private UserDetails ownerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;
}
