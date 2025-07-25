package com.example.reservation.decorators;

import com.example.reservation.domain.service.ReservationService;

import java.util.Date;
import java.util.UUID;

public class ValidationReservationServiceDecorator extends BaseReservationDecorator {

	public ValidationReservationServiceDecorator(ReservationService delegate) {
		super(delegate);
	}

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		if (roomId == null || guestId == null || checkIn == null || checkOut == null) {
			throw new IllegalArgumentException("[VALIDATION] Room ID, Guest ID, and dates must not be null");
		}

		if (!checkIn.before(checkOut)) {
			throw new IllegalArgumentException("[VALIDATION] Check-in date must be before check-out date");
		}

		System.out.println("[VALIDATION] Input data is valid.");
		super.createReservation(roomId, guestId, checkIn, checkOut);
	}
}
