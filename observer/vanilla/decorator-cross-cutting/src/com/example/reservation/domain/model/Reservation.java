package com.example.reservation.domain.model;

import java.util.Date;
import java.util.UUID;

public class Reservation {

	private final UUID reservationId;
	private final UUID roomId;
	private final UUID guestId;
	private final Date checkInDate;
	private final Date checkOutDate;

	private Reservation(UUID reservationId, UUID roomId, UUID guestId, Date checkInDate, Date checkOutDate) {
		this.reservationId = reservationId;
		this.roomId = roomId;
		this.guestId = guestId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}

	public static Reservation create(UUID roomId, UUID guestId, Date checkInDate, Date checkOutDate) {
		return new Reservation(UUID.randomUUID(), roomId, guestId, checkInDate, checkOutDate);
	}

	public UUID getReservationId() {
		return this.reservationId;
	}
}
