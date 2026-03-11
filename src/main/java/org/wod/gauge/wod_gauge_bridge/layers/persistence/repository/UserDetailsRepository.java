package org.wod.gauge.wod_gauge_bridge.layers.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wod.gauge.wod_gauge_bridge.layers.persistence.entity.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
}
