package com.germarna.patterns.observer.hexagonalddd.application.decorator;

import com.germarna.patterns.observer.hexagonalddd.domain.port.in.usecase.CreateReservationUseCase;

import java.util.Date;
import java.util.UUID;

public class AuthorizationCreateReservationServiceDecorator extends BaseReservationDecorator {

	public AuthorizationCreateReservationServiceDecorator(CreateReservationUseCase delegate) {
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
