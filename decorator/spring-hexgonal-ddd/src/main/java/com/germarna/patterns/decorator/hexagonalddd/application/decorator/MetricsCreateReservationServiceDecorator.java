package com.germarna.patterns.decorator.hexagonalddd.application.decorator;

import com.germarna.patterns.decorator.hexagonalddd.domain.port.in.usecase.CreateReservationUseCase;

import java.util.Date;
import java.util.UUID;

public class MetricsCreateReservationServiceDecorator extends BaseReservationDecorator {
	public MetricsCreateReservationServiceDecorator(CreateReservationUseCase delegate) {
		super(delegate);
	}

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		final long start = System.nanoTime();
		final long end = System.nanoTime();
		super.createReservation(roomId, guestId, checkIn, checkOut);
		System.out.println("[METRICS] createReservation took " + (end - start) + " ns");
	}
}