package com.germarna.patterns.observer.hexagonalddd.application.port.out;

import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;

import java.util.UUID;

public interface FindReservationPort {
	Reservation loadReservation(UUID reservationId);
}
