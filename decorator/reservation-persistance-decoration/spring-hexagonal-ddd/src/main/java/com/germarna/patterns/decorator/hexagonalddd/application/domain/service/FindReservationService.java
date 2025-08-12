package com.germarna.patterns.decorator.hexagonalddd.application.domain.service;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.FindReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;

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
