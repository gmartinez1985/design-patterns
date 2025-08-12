package com.germarna.patterns.decorator.hexagonalddd.application.domain.service;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.ChangeDatesOfReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.UpdateReservationPort;

import java.util.Date;
import java.util.UUID;

public class ChangeDatesOfReservationService implements ChangeDatesOfReservationUseCase {
	private final FindReservationPort findReservationPort;
	private final UpdateReservationPort updateReservationPort;

	public ChangeDatesOfReservationService(FindReservationPort findReservationPort,
			UpdateReservationPort updateReservationPort) {
		this.findReservationPort = findReservationPort;
		this.updateReservationPort = updateReservationPort;
	}

	@Override
	public Reservation changeDatesOfReservation(UUID reservationId, Date newStartDate, Date newEndDate) {
		if (newStartDate == null || newEndDate == null) {
			throw new IllegalArgumentException("Start/End date can't be null");
		}
		if (!newStartDate.before(newEndDate)) {
			throw new IllegalArgumentException("Check-in date must be before check-out date");
		}

		final Reservation reservation = this.findReservationPort.loadReservation(reservationId);
		if (reservation != null) {
			reservation.changeDates(newStartDate, newEndDate);
			return this.updateReservationPort.updateReservation(reservation);
		}
		return null;
	}
}
