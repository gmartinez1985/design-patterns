package com.example.mediator.reservation.model;

import java.util.Date;
import java.util.UUID;

public class Reservation {

	private final UUID reservationId;
	private final UUID roomId;
	private final UUID guestId;
	private final Date checkInDate;
	private final Date checkOutDate;

	public Reservation(UUID reservationId, UUID roomId, UUID guestId, Date checkInDate, Date checkOutDate) {
		this.reservationId = reservationId;
		this.roomId = roomId;
		this.guestId = guestId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}

	public UUID getReservationId() {
		return this.reservationId;
	}
}
