package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.updatereservation.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.UpdateReservationPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseUpdateReservationPersistenceDecorator implements UpdateReservationPort {
	private final UpdateReservationPort delegate;

	public Reservation updateReservation(Reservation reservation) {
		return this.delegate.updateReservation(reservation);
	}

}
