package com.germarna.patterns.observer.hexagonalddd.application.decorator;

import com.germarna.patterns.observer.hexagonalddd.domain.port.in.usecase.CreateReservationUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

public class TransactionalCreateReservationDecorator extends BaseReservationDecorator {

	public TransactionalCreateReservationDecorator(CreateReservationUseCase delegate) {
		super(delegate);
	}

	@Override
	@Transactional
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		System.out.println("[TRANSACTIONAL] Starting transactional createReservation");
		super.createReservation(roomId, guestId, checkIn, checkOut);
	}
}
