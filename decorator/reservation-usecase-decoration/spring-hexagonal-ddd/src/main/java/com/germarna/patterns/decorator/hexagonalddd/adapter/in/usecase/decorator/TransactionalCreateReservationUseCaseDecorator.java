package com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.port.in.CreateReservationUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

public class TransactionalCreateReservationUseCaseDecorator extends BaseCreateReservationUseCaseDecorator {

	public TransactionalCreateReservationUseCaseDecorator(CreateReservationUseCase delegate) {
		super(delegate);
	}

	@Override
	@Transactional
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		System.out.println("[TRANSACTIONAL] Starting transactional createReservation");
		super.createReservation(roomId, guestId, checkIn, checkOut);
	}
}
