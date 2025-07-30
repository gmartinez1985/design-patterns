package com.germarna.patterns.decorator.hexagonalddd.application.decorator;

import com.germarna.patterns.decorator.hexagonalddd.domain.port.in.usecase.CreateReservationUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseReservationDecorator implements CreateReservationUseCase {
	protected final CreateReservationUseCase delegate;

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		this.delegate.createReservation(roomId, guestId, checkIn, checkOut);
	}
}
