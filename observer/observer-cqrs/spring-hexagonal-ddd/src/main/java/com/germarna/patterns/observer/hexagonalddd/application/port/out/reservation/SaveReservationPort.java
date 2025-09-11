package com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation;

import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;

import java.util.UUID;

public interface SaveReservationPort {
	UUID saveReservation(Reservation reservation);
}
