package com.germarna.patterns.observer.hexagonalddd.adapter.in.usecase.decorator.savereservation;

import com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase.CreateReservationUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

public class TransactionalCreateReservationUseCaseDecorator extends BaseCreateReservationUseCaseDecorator {

	public TransactionalCreateReservationUseCaseDecorator(CreateReservationUseCase delegate) {
		super(delegate);
	}

	@Override
	@Transactional
	public UUID createReservation(UUID roomId, String guestName, String guestEmail, Date checkIn, Date checkOut) {
		return super.createReservation(roomId, guestName, guestEmail, checkIn, checkOut);
	}
}
