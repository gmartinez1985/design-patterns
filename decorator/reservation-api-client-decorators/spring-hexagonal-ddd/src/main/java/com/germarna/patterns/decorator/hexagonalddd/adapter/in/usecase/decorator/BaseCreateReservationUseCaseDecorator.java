package com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.port.in.usecase.CreateReservationUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseCreateReservationUseCaseDecorator implements CreateReservationUseCase {
	protected final CreateReservationUseCase delegate;

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut, UUID reservationId) {
		this.delegate.createReservation(roomId, guestId, checkIn, checkOut, reservationId);
	}
}
