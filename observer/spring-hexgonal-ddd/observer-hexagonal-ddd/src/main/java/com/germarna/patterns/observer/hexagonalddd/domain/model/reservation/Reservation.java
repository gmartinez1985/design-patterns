package com.germarna.patterns.observer.hexagonalddd.domain.model.reservation;

import com.germarna.patterns.observer.hexagonalddd.domain.events.reservation.ReservationCreatedEvent;
import com.germarna.patterns.observer.hexagonalddd.domain.shared.events.Subject;
import com.germarna.patterns.observer.hexagonalddd.domain.shared.model.AggregateRoot;

import java.util.UUID;

public class Reservation implements AggregateRoot {
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
		System.out.println("Reservation " + reservationId + " created for " + guestName);
		Subject.INSTANCE.notifyObservers(new ReservationCreatedEvent(reservationId, guestName, roomType));

		return reservation;
	}
}
