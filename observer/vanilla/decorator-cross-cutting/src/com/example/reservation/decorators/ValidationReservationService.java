package com.example.reservation.decorators;

import com.example.reservation.domain.service.ReservationService;

import java.util.Date;
import java.util.UUID;

public class ValidationReservationService implements ReservationService {

	private final ReservationService delegate;

	public ValidationReservationService(ReservationService delegate) {
		this.delegate = delegate;
	}

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		if (roomId == null || guestId == null || checkIn == null || checkOut == null) {
			throw new IllegalArgumentException("[VALIDATION] Room ID, Guest ID, and dates must not be null");
		}

		if (!checkIn.before(checkOut)) {
			throw new IllegalArgumentException("[VALIDATION] Check-in date must be before check-out date");
		}

		// if (checkIn.before(new Date())) {
		// throw new IllegalArgumentException("[VALIDATION] Check-in date cannot be in
		// the past");
		// }

		System.out.println("[VALIDATION] Input data is valid.");
		this.delegate.createReservation(roomId, guestId, checkIn, checkOut);
	}
}
