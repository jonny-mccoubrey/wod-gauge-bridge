package org.wod.gauge.wod_gauge_bridge.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wod.gauge.wod_gauge_bridge.controller.dto.CreateEventRequest;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.Affiliate;
import org.wod.gauge.wod_gauge_bridge.persistence.entity.Event;
import org.wod.gauge.wod_gauge_bridge.persistence.repository.AffiliateRepository;
import org.wod.gauge.wod_gauge_bridge.persistence.repository.EventRepository;
import org.wod.gauge.wod_gauge_bridge.util.exception.AffiliateNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private AffiliateRepository affiliateRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void createEvent_shouldSaveEntityAndReturnResponse() {
        final Affiliate affiliate = Affiliate.builder().affiliateId(4L).build();

        final CreateEventRequest req = new CreateEventRequest(4L, "New Event");

        when(affiliateRepository.findById(anyLong())).thenReturn(Optional.ofNullable(affiliate));

        when(eventRepository.save(any(Event.class)))
                .thenAnswer(invocation -> {
                   final Event e = invocation.getArgument(0);
                   return e.toBuilder().eventId(42L).build();
                });

        eventService.createEvent(req);

        final ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository, times(1)).save(captor.capture());

        final Event saved = captor.getValue();
        assertThat(saved.getAffiliate().getAffiliateId()).isEqualTo(4L);
        assertThat(saved.getTitle()).isEqualTo("New Event");
    }

    @Test
    void createEvent_shouldThrowAffiliateNotFoundException() {
        final CreateEventRequest req = CreateEventRequest.builder().affiliateId(1L).build();
        when(affiliateRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AffiliateNotFoundException.class, () ->
                eventService.createEvent(req)
        );

        verify(affiliateRepository).findById(1L);
        verifyNoInteractions(eventRepository);
    }
}