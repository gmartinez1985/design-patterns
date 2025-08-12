package com.germarna.patterns.decorator.hexagonalddd.application.domain.service;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.SaveReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveReservationPort;

import java.util.Date;
import java.util.UUID;

public class SaveReservationService implements SaveReservationUseCase {

	private final SaveReservationPort saveReservationPort;
	public SaveReservationService(SaveReservationPort saveReservationPort) {
		this.saveReservationPort = saveReservationPort;
	}

	@Override
	public UUID createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		final Reservation reservation = new Reservation(UUID.randomUUID(), roomId, guestId, checkIn, checkOut);
		final UUID reservationId = this.saveReservationPort.saveReservation(reservation);
		System.out.println("\t✅ Reservation created with ID: " + reservation.getReservationId());
		return reservationId;
	}
}
