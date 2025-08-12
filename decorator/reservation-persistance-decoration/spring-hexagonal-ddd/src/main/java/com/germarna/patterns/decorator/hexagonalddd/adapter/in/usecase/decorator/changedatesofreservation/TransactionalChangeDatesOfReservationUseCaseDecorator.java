package com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.changedatesofreservation;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.ChangeDatesOfReservationUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

public class TransactionalChangeDatesOfReservationUseCaseDecorator
		extends
			BaseChangeDatesOfReservationUseCaseDecorator {

	public TransactionalChangeDatesOfReservationUseCaseDecorator(ChangeDatesOfReservationUseCase delegate) {
		super(delegate);
	}

	@Override
	@Transactional
	public Reservation changeDatesOfReservation(UUID reservationId, Date newStartDate, Date newEndDate) {
		System.out.println("[TRANSACTIONAL] Starting transactional changeDatesOfReservation");
		final Reservation reservation = super.changeDatesOfReservation(reservationId, newStartDate, newEndDate);
		System.out.println("[TRANSACTIONAL] Comitting transactional changeDatesOfReservation");
		return reservation;
	}
}
