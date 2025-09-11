package com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation;

import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.application.domain.response.ReservationView;

import java.util.UUID;

public interface FindReservationPort {
	ReservationView loadReservation(UUID reservationId);
}
