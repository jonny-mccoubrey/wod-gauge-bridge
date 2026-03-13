package org.wod.gauge.wod_gauge_bridge.util.exception;

import org.wod.gauge.wod_gauge_bridge.persistence.entity.Affiliate;

public class AffiliateNotFoundException extends RuntimeException {

    public AffiliateNotFoundException(final Long id, final Class<Affiliate> affiliateClass) {
        super(affiliateClass.getSimpleName() + " with ID " + id + " was not found.");
    }
}
