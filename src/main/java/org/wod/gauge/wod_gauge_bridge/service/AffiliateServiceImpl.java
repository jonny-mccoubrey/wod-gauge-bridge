package org.wod.gauge.wod_gauge_bridge.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.wod.gauge.wod_gauge_bridge.controller.dto.AffiliateResponse;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateAffiliateRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateAffiliateUserRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.UserResponse;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.AffiliateUser;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.AffiliateUserDetailsId;
import org.wod.gauge.wod_gauge_bridge.persistence.repository.AffiliateUserRepository;
import org.wod.gauge.wod_gauge_bridge.util.exception.AffiliateNotFoundException;
import org.wod.gauge.wod_gauge_bridge.util.exception.DuplicateAssociationException;
import org.wod.gauge.wod_gauge_bridge.util.exception.UserNotFoundException;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.Affiliate;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.UserDetails;
import org.wod.gauge.wod_gauge_bridge.persistence.repository.AffiliateRepository;
import org.wod.gauge.wod_gauge_bridge.persistence.repository.UserDetailsRepository;

import java.util.List;

@Service
public class AffiliateServiceImpl implements AffiliateService {
    final UserDetailsRepository userRepository;
    final AffiliateRepository affiliateRepository;
    final AffiliateUserRepository affiliateUserRepository;

    public AffiliateServiceImpl(final UserDetailsRepository userRepository,
                                final AffiliateRepository affiliateRepository,
                                final AffiliateUserRepository affiliateUserRepository) {
        this.userRepository = userRepository;
        this.affiliateRepository = affiliateRepository;
        this.affiliateUserRepository = affiliateUserRepository;
    }

    @Override
    public AffiliateResponse createAffiliate(final CreateAffiliateRequest request) {
        final UserDetails affiliateOwner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new UserNotFoundException(request.getOwnerId(), UserDetails.class));

        final Affiliate affiliate = Affiliate.builder()
                .ownerId(affiliateOwner)
                .name(request.getName())
                .country(request.getCountry())
                .build();

        affiliateRepository.save(affiliate);

        return AffiliateResponse.builder()
                .affiliateId(affiliate.getAffiliateId())
                .name(affiliate.getName())
                .build();
    }

    @Override
    public void addUserToAffiliate(final CreateAffiliateUserRequest request) {
        final Affiliate affiliate = affiliateRepository.findById(request.getAffiliateId())
                .orElseThrow(() -> new AffiliateNotFoundException(request.getAffiliateId(), Affiliate.class));

        final UserDetails userDetails = userRepository.findById(request.getUserDetailsId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserDetailsId(), UserDetails.class));

        final AffiliateUser affiliateUser = AffiliateUser.builder()
                .id(new AffiliateUserDetailsId(null, null))
                .affiliate(affiliate)
                .userDetails(userDetails)
                .build();

        try {
            affiliateUserRepository.save(affiliateUser);
        } catch (final DataIntegrityViolationException ex) {
            throw new DuplicateAssociationException(
                    String.format("User (ID: %s) is already a member of Affiliate (ID: %s)",
                            userDetails.getUserDetailsId(), affiliate.getAffiliateId()));
        }
    }

    @Override
    public List<UserResponse> getAffiliateMembers(final Long affiliateId) {
        if(!affiliateRepository.existsById(affiliateId)) throw new AffiliateNotFoundException(affiliateId, Affiliate.class);

        final List<UserDetails> users = affiliateUserRepository.findByAffiliateAffiliateId(affiliateId)
                .stream()
                .map(AffiliateUser::getUserDetails)
                .toList();

        return users.stream()
                .map(userEntity -> UserResponse.builder()
                        .userDetailsId(userEntity.getUserDetailsId())
                        .username(userEntity.getUsername())
                        .emailAddress(userEntity.getEmailAddress())
                        .name(userEntity.getName())
                        .build())
                .toList();
    }
}
