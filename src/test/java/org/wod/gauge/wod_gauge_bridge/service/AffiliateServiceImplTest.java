package org.wod.gauge.wod_gauge_bridge.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateAffiliateRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateAffiliateUserRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.UserResponse;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.Affiliate;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.AffiliateUser;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.UserDetails;
import org.wod.gauge.wod_gauge_bridge.persistence.repository.AffiliateRepository;
import org.wod.gauge.wod_gauge_bridge.persistence.repository.AffiliateUserRepository;
import org.wod.gauge.wod_gauge_bridge.persistence.repository.UserDetailsRepository;
import org.wod.gauge.wod_gauge_bridge.util.exception.AffiliateNotFoundException;
import org.wod.gauge.wod_gauge_bridge.util.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AffiliateServiceImplTest {
    @Mock
    private UserDetailsRepository userRepository;

    @Mock
    private AffiliateRepository affiliateRepository;

    @Mock
    private AffiliateUserRepository affiliateUserRepository;

    @InjectMocks
    private AffiliateServiceImpl affiliateService;

    @Test
    void createAffiliate_shouldSaveEntityAndReturnResponse() {
        final UserDetails affiliateOwner = UserDetails.builder().userDetailsId(1L).build();

        final CreateAffiliateRequest req = CreateAffiliateRequest.builder()
                .ownerId(1L)
                .name("Test Affiliate")
                .country("United Kingdom")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(affiliateOwner));

        when(affiliateRepository.save(any(Affiliate.class)))
                .thenAnswer(invocation -> {
                    final Affiliate a = invocation.getArgument(0);
                    return a.toBuilder().affiliateId(42L).build();
                });

        affiliateService.createAffiliate(req);

        final ArgumentCaptor<Affiliate> captor = ArgumentCaptor.forClass(Affiliate.class);
        verify(affiliateRepository, times(1)).save(captor.capture());

        final Affiliate saved = captor.getValue();
        assertThat(saved.getOwner().getUserDetailsId()).isEqualTo(1L);
        assertThat(saved.getName()).isEqualTo("Test Affiliate");
        assertThat(saved.getCountry()).isEqualTo("United Kingdom");
    }

    @Test
    void createAffiliate_shouldThrowUserNotFoundException() {
        final CreateAffiliateRequest req = CreateAffiliateRequest.builder()
                .ownerId(1L)
                .name("Test Affiliate")
                .country("United Kingdom")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                affiliateService.createAffiliate(req)
        );

        verify(userRepository).findById(1L);
        verifyNoInteractions(affiliateRepository);
    }

    @Test
    void addUserToAffiliate_shouldSaveEntityAndReturnResponse() {
        final Affiliate affiliate = Affiliate.builder().affiliateId(1L).build();
        final UserDetails affiliateOwner = UserDetails.builder().userDetailsId(1L).build();

        final CreateAffiliateUserRequest req = CreateAffiliateUserRequest.builder()
                .affiliateId(1L)
                .userDetailsId(1L)
                .build();

        when(affiliateRepository.findById(anyLong())).thenReturn(Optional.ofNullable(affiliate));
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(affiliateOwner));

        when(affiliateUserRepository.save(any(AffiliateUser.class)))
                .thenAnswer(invocation -> {
                    final AffiliateUser au = invocation.getArgument(0);
                    return au.toBuilder().build();
                });

        affiliateService.addUserToAffiliate(req);

        final ArgumentCaptor<AffiliateUser> captor = ArgumentCaptor.forClass(AffiliateUser.class);
        verify(affiliateUserRepository, times(1)).save(captor.capture());

        final AffiliateUser saved = captor.getValue();
        assertThat(saved.getAffiliate().getAffiliateId()).isEqualTo(1L);
        assertThat(saved.getUserDetails().getUserDetailsId()).isEqualTo(1L);
    }

    @Test
    void addUserToAffiliate_shouldThrowAffiliateNotFoundException() {
        final CreateAffiliateUserRequest req = CreateAffiliateUserRequest.builder()
                .affiliateId(1L)
                .userDetailsId(1L)
                .build();

        when(affiliateRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AffiliateNotFoundException.class, () ->
                affiliateService.addUserToAffiliate(req)
        );

        verify(affiliateRepository).findById(1L);
        verifyNoInteractions(affiliateUserRepository);
    }

    @Test
    void addUserToAffiliate_shouldThrowUserNotFoundException() {
        final Affiliate affiliate = Affiliate.builder().affiliateId(1L).build();

        final CreateAffiliateUserRequest req = CreateAffiliateUserRequest.builder()
                .affiliateId(1L)
                .userDetailsId(1L)
                .build();

        when(affiliateRepository.findById(anyLong())).thenReturn(Optional.ofNullable(affiliate));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                affiliateService.addUserToAffiliate(req)
        );

        verify(userRepository).findById(1L);
        verifyNoInteractions(affiliateUserRepository);
    }

    @Test
    void getAffiliateMembers_shouldReturnListOfMembers() {
        final Affiliate affiliate = Affiliate.builder().affiliateId(1L).build();

        final UserDetails expectedUserEntity = UserDetails.builder()
                .userDetailsId(123L)
                .emailAddress("new-user@test.com")
                .build();

        when(affiliateRepository.existsById(anyLong())).thenReturn(true);

        when(affiliateUserRepository.findByAffiliateAffiliateId(anyLong()))
                .thenReturn(List.of(AffiliateUser.builder()
                        .userDetails(expectedUserEntity)
                        .build()));

        final List<UserResponse> actual = affiliateService.getAffiliateMembers(1L);
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.getFirst().getUserDetailsId()).isEqualTo(123L);
        assertThat(actual.getFirst().getEmailAddress()).isEqualTo("new-user@test.com");
    }

    @Test
    void getAffiliateMembers_shouldThrowAffiliateNotFoundException() {
        when(affiliateRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(AffiliateNotFoundException.class, () ->
                affiliateService.getAffiliateMembers(1L)
        );

        verify(affiliateRepository).existsById(1L);
        verifyNoInteractions(affiliateUserRepository);
    }
}
