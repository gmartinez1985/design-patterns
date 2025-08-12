package com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.savereservation;

import com.germarna.patterns.decorator.hexagonalddd.application.port.in.SaveReservationUseCase;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseSaveReservationUseCaseDecorator implements SaveReservationUseCase {
	protected final SaveReservationUseCase delegate;

	@Override
	public UUID createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		return this.delegate.createReservation(roomId, guestId, checkIn, checkOut);
	}
}
