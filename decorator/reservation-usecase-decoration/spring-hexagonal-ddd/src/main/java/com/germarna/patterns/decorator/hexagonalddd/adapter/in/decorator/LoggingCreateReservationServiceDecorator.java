package com.germarna.patterns.decorator.hexagonalddd.adapter.in.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.port.in.CreateReservationUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

public class LoggingCreateReservationServiceDecorator extends BaseReservationDecorator {
	private static final Logger log = LoggerFactory.getLogger(LoggingCreateReservationServiceDecorator.class);

	public LoggingCreateReservationServiceDecorator(CreateReservationUseCase delegate) {
		super(delegate);
	}

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		log.info("[LOG] Creating reservation for guest {} in room {}", guestId, roomId);
		super.createReservation(roomId, guestId, checkIn, checkOut);
		log.info("[LOG] Reservation successfully created.");
	}
}
