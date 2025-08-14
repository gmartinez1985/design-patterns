package com.example.reservation.domain.model;

import java.util.Date;
import java.util.UUID;

public class BasicReservation implements ReservationComponent {

	private final UUID reservationId;
	private final UUID roomId;
	private final UUID guestId;
	private final Date checkInDate;
	private final Date checkOutDate;

	public BasicReservation(UUID reservationId, UUID roomId, UUID guestId, Date checkInDate, Date checkOutDate) {
		this.reservationId = reservationId;
		this.roomId = roomId;
		this.guestId = guestId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}

	public UUID getReservationId() {
		return this.reservationId;
	}

	@Override
	public double getCost() {
		return 100.0;
	}

	@Override
	public String getDescription() {
		return "Basic Room Reservation";
	}
}
