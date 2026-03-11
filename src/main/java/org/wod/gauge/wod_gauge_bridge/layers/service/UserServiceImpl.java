package org.wod.gauge.wod_gauge_bridge.layers.service;

import org.springframework.stereotype.Service;
import org.wod.gauge.wod_gauge_bridge.dto.CreateUserRequest;
import org.wod.gauge.wod_gauge_bridge.dto.UserResponse;
import org.wod.gauge.wod_gauge_bridge.layers.persistence.entity.UserDetails;
import org.wod.gauge.wod_gauge_bridge.layers.persistence.repository.UserDetailsRepository;

@Service
public class UserServiceImpl implements UserService {

    final UserDetailsRepository userRepository;

    public UserServiceImpl(final UserDetailsRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        final UserDetails user = UserDetails.builder()
                .username(request.getUsername())
                .emailAddress(request.getEmailAddress())
                .password(request.getPassword())
                .name(request.getName())
                .build();
        userRepository.save(user);

        return UserResponse.builder()
                .userDetailsId(user.getUserDetailsId())
                .username(user.getUsername())
                .emailAddress(user.getEmailAddress())
                .build();
    }
}
