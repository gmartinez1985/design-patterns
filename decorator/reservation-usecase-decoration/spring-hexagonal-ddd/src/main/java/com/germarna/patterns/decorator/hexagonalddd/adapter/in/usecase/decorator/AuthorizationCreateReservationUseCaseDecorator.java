package com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.port.in.CreateReservationUseCase;

import java.util.Date;
import java.util.UUID;

public class AuthorizationCreateReservationUseCaseDecorator extends BaseCreateReservationUseCaseDecorator {

	public AuthorizationCreateReservationUseCaseDecorator(CreateReservationUseCase delegate) {
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
