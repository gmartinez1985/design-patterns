package com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.port.in.CreateReservationUseCase;

import java.util.Date;
import java.util.UUID;

public class ValidationCreateReservationUseCaseDecorator extends BaseCreateReservationUseCaseDecorator {

	public ValidationCreateReservationUseCaseDecorator(CreateReservationUseCase delegate) {
		super(delegate);
	}

	@Override
	public UUID createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		if (roomId == null || guestId == null || checkIn == null || checkOut == null) {
			throw new IllegalArgumentException("[VALIDATION] Room ID, Guest ID, and dates must not be null");
		}

		if (!checkIn.before(checkOut)) {
			throw new IllegalArgumentException("[VALIDATION] Check-in date must be before check-out date");
		}

		System.out.println("[VALIDATION] Input data is valid.");
		return super.createReservation(roomId, guestId, checkIn, checkOut);
	}
}
