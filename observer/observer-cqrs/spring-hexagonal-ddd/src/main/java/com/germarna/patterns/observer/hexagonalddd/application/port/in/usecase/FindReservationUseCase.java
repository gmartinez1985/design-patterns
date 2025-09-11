package com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase;

import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.application.domain.response.ReservationView;

import java.util.UUID;

public interface FindReservationUseCase {
	ReservationView findReservation(UUID reservationId);
}
