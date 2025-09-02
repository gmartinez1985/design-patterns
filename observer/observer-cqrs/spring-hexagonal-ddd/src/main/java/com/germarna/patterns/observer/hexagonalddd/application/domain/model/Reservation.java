package com.germarna.patterns.observer.hexagonalddd.application.domain.model;

import com.germarna.patterns.observer.hexagonalddd.shared.domain.model.AggregateRoot;

import java.util.Date;
import java.util.UUID;

public class Reservation implements AggregateRoot {

	private final UUID reservationId;
	private final UUID roomId;
	private final String guestName;
	private final String guestEmail;
	private Date checkInDate;
	private Date checkOutDate;

	public Reservation(UUID reservationId, UUID roomId, String guestName, String guestEmail, Date checkInDate,
			Date checkOutDate) {
		this.reservationId = reservationId;
		this.roomId = roomId;
		this.guestName = guestName;
		this.guestEmail = guestEmail;
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

	public String getGuestName() {
		return this.guestName;
	}

	public String getGuestEmail() {
		return this.guestEmail;
	}

	public Date getCheckInDate() {
		return this.checkInDate;
	}

	public Date getCheckOutDate() {
		return this.checkOutDate;
	}
}
