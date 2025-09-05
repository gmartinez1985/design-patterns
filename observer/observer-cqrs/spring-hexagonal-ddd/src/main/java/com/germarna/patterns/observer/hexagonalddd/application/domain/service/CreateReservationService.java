package com.germarna.patterns.observer.hexagonalddd.application.domain.service;

import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase.CreateReservationUseCase;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation.SaveReservationPort;

import java.util.Date;
import java.util.UUID;

public class CreateReservationService implements CreateReservationUseCase {
	private final SaveReservationPort saveReservationPort;

	public CreateReservationService(SaveReservationPort saveReservationPort) {
		this.saveReservationPort = saveReservationPort;
	}

	@Override
	public UUID createReservation(UUID roomId, String guestName, String guestEmail, Date checkIn, Date checkOut) {
		final Reservation reservation = new Reservation(UUID.randomUUID(), roomId, guestName, guestEmail, checkIn,
				checkOut);
		return this.saveReservationPort.saveReservation(reservation);
	}
}
