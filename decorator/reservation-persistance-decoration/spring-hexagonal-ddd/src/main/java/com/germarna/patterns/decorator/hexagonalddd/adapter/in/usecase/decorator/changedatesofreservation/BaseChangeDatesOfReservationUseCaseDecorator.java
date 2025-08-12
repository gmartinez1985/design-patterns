package com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.changedatesofreservation;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.ChangeDatesOfReservationUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseChangeDatesOfReservationUseCaseDecorator implements ChangeDatesOfReservationUseCase {
	protected final ChangeDatesOfReservationUseCase delegate;

	@Override
	public Reservation changeDatesOfReservation(UUID reservationId, Date newStartDate, Date newEndDate) {
		return this.delegate.changeDatesOfReservation(reservationId, newStartDate, newEndDate);
	}
}
