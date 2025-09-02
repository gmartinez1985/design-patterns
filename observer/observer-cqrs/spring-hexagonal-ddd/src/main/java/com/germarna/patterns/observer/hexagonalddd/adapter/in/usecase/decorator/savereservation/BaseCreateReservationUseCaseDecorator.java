package com.germarna.patterns.observer.hexagonalddd.adapter.in.usecase.decorator.savereservation;

import com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase.CreateReservationUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseCreateReservationUseCaseDecorator implements CreateReservationUseCase {
	protected final CreateReservationUseCase delegate;

	@Override
	public UUID createReservation(UUID roomId, String guestName, String guestEmail, Date checkIn, Date checkOut) {
		return this.delegate.createReservation(roomId, guestName, guestEmail, checkIn, checkOut);
	}
}
