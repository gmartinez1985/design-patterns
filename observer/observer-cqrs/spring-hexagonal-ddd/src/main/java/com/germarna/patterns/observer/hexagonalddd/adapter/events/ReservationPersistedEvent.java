package com.germarna.patterns.observer.hexagonalddd.adapter.events;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public record ReservationPersistedEvent(UUID reservationId, UUID roomId, Date checkIn, Date checkOut, String guestName,
		String guestEmail, Instant timestamp) {
}
