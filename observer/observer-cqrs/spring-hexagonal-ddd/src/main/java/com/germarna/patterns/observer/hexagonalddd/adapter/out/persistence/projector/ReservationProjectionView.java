package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public record ReservationProjectionView(UUID reservationId, String guestName, String guestEmail, Date checkInDate,
		Date checkOutDate, RoomView room, Instant timestamp) {
	public record RoomView(UUID id, String type, String number) {

	}
}
