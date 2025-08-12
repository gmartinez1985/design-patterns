package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.findreservation.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseFindReservationPersistenceAdapterDecorator implements FindReservationPort {
	private final FindReservationPort delegate;

	@Override
	public Reservation loadReservation(UUID reservationId) {
		return this.delegate.loadReservation(reservationId);
	}

}
