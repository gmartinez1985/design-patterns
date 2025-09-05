package com.germarna.patterns.observer.hexagonalddd.application.domain.service;

import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase.FindReservationUseCase;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation.FindReservationPort;

import java.util.UUID;

public class FindReservationService implements FindReservationUseCase {

	private final FindReservationPort findReservationPort;
	public FindReservationService(FindReservationPort findReservationPort) {
		this.findReservationPort = findReservationPort;
	}

	@Override
	public Reservation findReservation(UUID reservationId) {
		return this.findReservationPort.loadReservation(reservationId);
	}
}
