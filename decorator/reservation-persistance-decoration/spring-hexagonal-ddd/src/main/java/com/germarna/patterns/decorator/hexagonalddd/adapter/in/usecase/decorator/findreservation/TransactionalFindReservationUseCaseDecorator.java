package com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.findreservation;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.FindReservationUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class TransactionalFindReservationUseCaseDecorator extends BaseFindReservationUseCaseDecorator {

	public TransactionalFindReservationUseCaseDecorator(FindReservationUseCase delegate) {
		super(delegate);
	}

	@Override
	@Transactional(readOnly = true)
	public Reservation findReservation(UUID reservationId) {
		System.out.println("[TRANSACTIONAL] Starting transactional findReservation");
		final Reservation reservation = super.findReservation(reservationId);
		System.out.println("[TRANSACTIONAL] Comitting transactional findReservation");
		return reservation;
	}
}
