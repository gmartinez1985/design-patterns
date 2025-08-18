package com.germarna.patterns.observer.hexagonalddd.application.domain.service;

import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.CreateReservationUseCase;

import java.util.UUID;

public class CreateReservationService implements CreateReservationUseCase {

	@Override
	public UUID createReservation(String guestName, String roomType) {
		final Reservation reservation = Reservation.create(guestName, roomType);

		System.out.println("\t✅ Reservation created with ID: " + reservation.getReservationId());

		return reservation.getReservationId();
	}
}
