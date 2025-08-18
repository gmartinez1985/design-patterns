package com.germarna.patterns.observer.hexagonalddd.application.domain.model;

import com.germarna.patterns.observer.hexagonalddd.application.domain.events.ReservationCreatedEvent;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.model.AggregateRoot;

import java.util.UUID;

public class Reservation extends AggregateRoot {

	private final UUID reservationId;
	private final String guestName;
	private final String roomType;

	private Reservation(UUID reservationId, String guestName, String roomType) {
		this.reservationId = reservationId;
		this.guestName = guestName;
		this.roomType = roomType;
	}
	public static Reservation create(String guestName, String roomType) {
		final UUID reservationId = UUID.randomUUID();
		final Reservation reservation = new Reservation(reservationId, guestName, roomType);
		reservation.publish(new ReservationCreatedEvent(reservationId, guestName, roomType));

		return reservation;
	}
	public UUID getReservationId() {
		return this.reservationId;
	}
}
