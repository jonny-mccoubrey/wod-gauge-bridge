package org.wod.gauge.wod_gauge_bridge.layers.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDetailsId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;
}
