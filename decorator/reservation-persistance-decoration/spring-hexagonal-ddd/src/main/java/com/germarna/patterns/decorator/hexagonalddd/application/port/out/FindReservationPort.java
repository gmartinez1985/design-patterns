package com.germarna.patterns.decorator.hexagonalddd.application.port.out;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;

import java.util.UUID;

public interface FindReservationPort {
	Reservation loadReservation(UUID reservationId);
}
