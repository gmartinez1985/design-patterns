package com.germarna.patterns.observer.hexagonalddd.application.domain.events;

import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.DomainEvent;

import java.util.UUID;

public record ReservationCreatedEvent(UUID reservationId, String guestName, String roomType) implements DomainEvent {
}
