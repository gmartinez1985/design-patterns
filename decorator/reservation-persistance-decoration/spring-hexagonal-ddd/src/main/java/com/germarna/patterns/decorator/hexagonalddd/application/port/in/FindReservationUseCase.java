package com.germarna.patterns.decorator.hexagonalddd.application.port.in;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;

import java.util.UUID;

public interface FindReservationUseCase {
	Reservation findReservation(UUID reservationId);
}
