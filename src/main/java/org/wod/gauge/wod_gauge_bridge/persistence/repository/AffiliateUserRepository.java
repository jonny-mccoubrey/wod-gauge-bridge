package org.wod.gauge.wod_gauge_bridge.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.AffiliateUser;

@Repository
public interface AffiliateUserRepository extends JpaRepository<AffiliateUser, Long> {
}
