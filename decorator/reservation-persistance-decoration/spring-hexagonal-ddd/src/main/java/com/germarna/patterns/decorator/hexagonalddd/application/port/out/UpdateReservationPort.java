package com.germarna.patterns.decorator.hexagonalddd.application.port.out;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;

public interface UpdateReservationPort {
	Reservation updateReservation(Reservation reservation);
}
