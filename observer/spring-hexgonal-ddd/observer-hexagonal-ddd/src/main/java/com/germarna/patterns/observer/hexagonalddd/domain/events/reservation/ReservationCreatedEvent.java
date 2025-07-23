package com.germarna.patterns.observer.hexagonalddd.domain.events.reservation;

import com.germarna.patterns.observer.hexagonalddd.domain.shared.events.DomainEvent;

import java.util.UUID;

public class ReservationCreatedEvent implements DomainEvent {
	private final UUID reservationId;
	private final String guestName;

	private final String roomType;

	public ReservationCreatedEvent(UUID reservationId, String guestName, String roomType) {
		this.reservationId = reservationId;
		this.guestName = guestName;
		this.roomType = roomType;
	}

	public UUID getReservationId() {
		return this.reservationId;
	}
	public String getGuestName() {
		return this.guestName;
	}

	public String getRoomType() {
		return this.roomType;
	}
}
