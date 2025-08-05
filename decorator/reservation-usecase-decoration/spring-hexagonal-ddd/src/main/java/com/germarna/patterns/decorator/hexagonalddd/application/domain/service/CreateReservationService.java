package com.germarna.patterns.decorator.hexagonalddd.application.domain.service;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.CreateReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveDummyPort;

import java.util.Date;
import java.util.UUID;

public class CreateReservationService implements CreateReservationUseCase {

	private final SaveDummyPort saveDummyPort;
	private boolean firstCall = true;

	public CreateReservationService(SaveDummyPort saveDummyPort) {
		this.saveDummyPort = saveDummyPort;
	}

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		final Reservation reservation = new Reservation(UUID.randomUUID(), roomId, guestId, checkIn, checkOut);

		if (this.firstCall) {
			this.saveDummyPort.save("First insert");
			this.saveDummyPort.save("Second insert");
			this.firstCall = false;
		} else {
			this.saveDummyPort.save("Third insert");
			throw new RuntimeException("💥 Simulated failure on second call");
		}

		System.out.println("\t✅ Reservation created with ID: " + reservation.getReservationId());
	}
}
