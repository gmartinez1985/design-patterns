package com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.findreservation;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.FindReservationUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseFindReservationUseCaseDecorator implements FindReservationUseCase {
	protected final FindReservationUseCase delegate;

	@Override
	public Reservation findReservation(UUID reservationId) {
		return this.delegate.findReservation(reservationId);
	}
}
