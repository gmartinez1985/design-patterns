package com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.port.in.CreateReservationUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

public class LoggingCreateReservationUseCaseDecorator extends BaseCreateReservationUseCaseDecorator {
	private static final Logger log = LoggerFactory.getLogger(LoggingCreateReservationUseCaseDecorator.class);

	public LoggingCreateReservationUseCaseDecorator(CreateReservationUseCase delegate) {
		super(delegate);
	}

	@Override
	public UUID createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		log.info("[LOG] Creating reservation for guest {} in room {}", guestId, roomId);
		final UUID reservationId = super.createReservation(roomId, guestId, checkIn, checkOut);
		log.info("[LOG] Reservation successfully created.");
		return reservationId;
	}
}
