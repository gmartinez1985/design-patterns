package com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.port.in.CreateReservationUseCase;

import java.util.Date;
import java.util.UUID;

public class MetricsCreateReservationUseCaseDecorator extends BaseCreateReservationUseCaseDecorator {
	public MetricsCreateReservationUseCaseDecorator(CreateReservationUseCase delegate) {
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