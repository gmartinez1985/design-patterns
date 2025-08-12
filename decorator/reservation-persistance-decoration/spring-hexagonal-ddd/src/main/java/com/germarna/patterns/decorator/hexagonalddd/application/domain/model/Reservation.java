package com.germarna.patterns.decorator.hexagonalddd.application.domain.model;

import com.germarna.patterns.decorator.hexagonalddd.shared.domain.model.AggregateRoot;

import java.util.Date;
import java.util.UUID;

public class Reservation implements AggregateRoot {

	private final UUID reservationId;
	private final UUID roomId;
	private final UUID guestId;
	private Date checkInDate;
	private Date checkOutDate;

	public Reservation(UUID reservationId, UUID roomId, UUID guestId, Date checkInDate, Date checkOutDate) {
		this.reservationId = reservationId;
		this.roomId = roomId;
		this.guestId = guestId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}

	public void changeDates(Date newCheckInDate, Date newCheckOutDate) {
		if (newCheckInDate == null || newCheckOutDate == null) {
			throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
		}
		if (!newCheckInDate.before(newCheckOutDate)) {
			throw new IllegalArgumentException("Check-in date must be before check-out date");
		}
		this.checkInDate = newCheckInDate;
		this.checkOutDate = newCheckOutDate;
	}

	public UUID getReservationId() {
		return this.reservationId;
	}

	public UUID getRoomId() {
		return this.roomId;
	}

	public UUID getGuestId() {
		return this.guestId;
	}

	public Date getCheckInDate() {
		return this.checkInDate;
	}

	public Date getCheckOutDate() {
		return this.checkOutDate;
	}

	@Override
	public String toString() {
		return "Reservation{" + "reservationId=" + this.reservationId + ", roomId=" + this.roomId + ", guestId="
				+ this.guestId + ", checkInDate=" + this.checkInDate + ", checkOutDate=" + this.checkOutDate + '}';
	}
}
