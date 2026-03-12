package org.wod.gauge.wod_gauge_bridge.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.UserDetails;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(final Long id, final Class<UserDetails> userDetailsClass) {
        super(userDetailsClass.getSimpleName() + " with ID " + id + " was not found.");
    }
}
