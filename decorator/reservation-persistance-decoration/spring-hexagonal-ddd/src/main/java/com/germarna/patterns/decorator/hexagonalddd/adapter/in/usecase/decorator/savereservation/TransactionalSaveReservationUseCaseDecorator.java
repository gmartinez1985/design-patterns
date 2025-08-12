package com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.savereservation;

import com.germarna.patterns.decorator.hexagonalddd.application.port.in.SaveReservationUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

public class TransactionalSaveReservationUseCaseDecorator extends BaseSaveReservationUseCaseDecorator {

	public TransactionalSaveReservationUseCaseDecorator(SaveReservationUseCase delegate) {
		super(delegate);
	}

	@Override
	@Transactional
	public UUID createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		System.out.println("[TRANSACTIONAL] Starting transactional createReservation");
		final UUID reservationId = super.createReservation(roomId, guestId, checkIn, checkOut);
		System.out.println("[TRANSACTIONAL] Comitting transactional createReservation");
		return reservationId;
	}
}
