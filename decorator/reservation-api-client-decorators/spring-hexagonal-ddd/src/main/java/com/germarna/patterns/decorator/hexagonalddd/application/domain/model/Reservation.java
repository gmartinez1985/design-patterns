package com.germarna.patterns.decorator.hexagonalddd.application.domain.model;

import com.germarna.patterns.decorator.hexagonalddd.shared.domain.model.AggregateRoot;

import java.util.Date;
import java.util.UUID;

public class Reservation implements AggregateRoot {

	private final UUID reservationId;
	private final UUID roomId;
	private final UUID guestId;
	private final Date checkInDate;
	private final Date checkOutDate;
	private final double totalAmount;

	public Reservation(UUID reservationId, UUID roomId, UUID guestId, Date checkInDate, Date checkOutDate,
			double totalAmount) {
		this.reservationId = reservationId;
		this.roomId = roomId;
		this.guestId = guestId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.totalAmount = totalAmount;
	}

	public UUID getReservationId() {
		return this.reservationId;
	}

	public double getTotalAmount() {
		return this.totalAmount;
	}
}
