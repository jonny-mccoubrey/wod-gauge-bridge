package org.wod.gauge.wod_gauge_bridge.layers.service;

import org.springframework.stereotype.Service;
import org.wod.gauge.wod_gauge_bridge.dto.AffiliateResponse;
import org.wod.gauge.wod_gauge_bridge.dto.CreateAffiliateRequest;
import org.wod.gauge.wod_gauge_bridge.exception.UserNotFoundException;
import org.wod.gauge.wod_gauge_bridge.layers.persistence.entity.Affiliate;
import org.wod.gauge.wod_gauge_bridge.layers.persistence.entity.UserDetails;
import org.wod.gauge.wod_gauge_bridge.layers.persistence.repository.AffiliateRepository;
import org.wod.gauge.wod_gauge_bridge.layers.persistence.repository.UserDetailsRepository;

@Service
public class AffiliateServiceImpl implements AffiliateService {
    final UserDetailsRepository userRepository;
    final AffiliateRepository affiliateRepository;

    public AffiliateServiceImpl(final UserDetailsRepository userRepository, final AffiliateRepository affiliateRepository) {
        this.userRepository = userRepository;
        this.affiliateRepository = affiliateRepository;
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
}
