package org.wod.gauge.wod_gauge_bridge.util.exception;

import org.wod.gauge.wod_gauge_bridge.persistence.entity.UserDetails;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final Long id, final Class<UserDetails> userDetailsClass) {
        super(userDetailsClass.getSimpleName() + " with ID " + id + " was not found.");
    }
}
