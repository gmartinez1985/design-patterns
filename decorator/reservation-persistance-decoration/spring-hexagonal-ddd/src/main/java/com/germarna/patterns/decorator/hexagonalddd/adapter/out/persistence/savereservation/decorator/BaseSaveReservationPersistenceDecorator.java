package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.savereservation.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveReservationPort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseSaveReservationPersistenceDecorator implements SaveReservationPort {
	private final SaveReservationPort delegate;

	@Override
	public UUID saveReservation(Reservation reservation) {
		return this.delegate.saveReservation(reservation);
	}
}
