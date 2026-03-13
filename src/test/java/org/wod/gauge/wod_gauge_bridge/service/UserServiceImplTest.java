package org.wod.gauge.wod_gauge_bridge.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateUserRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.UserResponse;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.UserDetails;
import org.wod.gauge.wod_gauge_bridge.persistence.repository.UserDetailsRepository;

import static org.mockito.ArgumentMatchers.any;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserDetailsRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldSaveEntityAndReturnResponse() {
        final CreateUserRequest req = CreateUserRequest.builder()
                .username("new-user")
                .emailAddress("newuser@test.com")
                .password("password")
                .name("New User")
                .build();

        when(userRepository.save(any(UserDetails.class)))
                .thenAnswer(invocation -> {
                    final UserDetails u = invocation.getArgument(0);
                    return u.toBuilder().userDetailsId(42L).build();
                });

        final UserResponse res = userService.createUser(req);

        final ArgumentCaptor<UserDetails> captor = ArgumentCaptor.forClass(UserDetails.class);
        verify(userRepository, times(1)).save(captor.capture());
        verifyNoMoreInteractions(userRepository);

        final UserDetails saved = captor.getValue();
        assertThat(saved.getUsername()).isEqualTo("new-user");
        assertThat(saved.getEmailAddress()).isEqualTo("newuser@test.com");
        assertThat(saved.getPassword()).isEqualTo("password");
        assertThat(saved.getName()).isEqualTo("New User");
    }
}
