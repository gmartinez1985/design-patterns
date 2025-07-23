package com.example.reservation.domain.model;

import java.util.UUID;

public class Reservation {
	private final UUID reservationId;
	private final String guestName;
	private final String roomType;

	public Reservation(String guestName, String roomType) {
		this.reservationId = UUID.randomUUID();
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
