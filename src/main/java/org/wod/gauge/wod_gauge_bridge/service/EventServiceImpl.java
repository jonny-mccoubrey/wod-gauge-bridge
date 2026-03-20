package org.wod.gauge.wod_gauge_bridge.service;

import org.springframework.stereotype.Service;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateEventRequest;
import org.wod.gauge.wod_gauge_bridge.controller.dto.EventResponse;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.Affiliate;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.Event;
import org.wod.gauge.wod_gauge_bridge.persistence.repository.AffiliateRepository;
import org.wod.gauge.wod_gauge_bridge.persistence.repository.EventRepository;
import org.wod.gauge.wod_gauge_bridge.util.exception.AffiliateNotFoundException;

@Service
public class EventServiceImpl implements EventService {
    final AffiliateRepository affiliateRepository;
    final EventRepository eventRepository;

    public EventServiceImpl(final AffiliateRepository affiliateRepository,
                            final EventRepository eventRepository) {
        this.affiliateRepository = affiliateRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public EventResponse createEvent(final CreateEventRequest request) {
        final Affiliate affiliate = affiliateRepository.findById(request.getAffiliateId())
                .orElseThrow(() -> new AffiliateNotFoundException(request.getAffiliateId(), Affiliate.class));

        final Event event = Event.builder()
                .affiliate(affiliate)
                .title(request.getTitle())
                .build();

        eventRepository.save(event);

        return EventResponse.builder()
                .eventId(event.getEventId())
                .title(event.getTitle())
                .build();
    }
}
