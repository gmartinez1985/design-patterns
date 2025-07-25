package com.example.reservation.decorators;

import com.example.reservation.domain.service.ReservationService;

import java.util.Date;
import java.util.UUID;

public class AuthorizationReservationServiceDecorator extends BaseReservationDecorator {

	public AuthorizationReservationServiceDecorator(ReservationService delegate) {
		super(delegate);
	}

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		if (!this.isAuthorized(guestId)) {
			throw new SecurityException("Guest is not authorized.");
		}

		System.out.println("[AUTH] Guest " + guestId + " is authorized to create a reservation.");

		super.createReservation(roomId, guestId, checkIn, checkOut);
	}

	private boolean isAuthorized(UUID guestId) {
		// Dummy check for this example
		return true;
	}
}
