package com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase;

import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;

import java.util.UUID;

public interface FindReservationUseCase {
	Reservation findReservation(UUID reservationId);
}
