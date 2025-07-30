package com.example.reservation.decorators;

import com.example.reservation.domain.service.ReservationService;

import java.util.Date;
import java.util.UUID;

public class MetricsReservationServiceDecorator extends BaseReservationDecorator {

	public MetricsReservationServiceDecorator(ReservationService delegate) {
		super(delegate);
	}
	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		final long start = System.nanoTime();
		super.createReservation(roomId, guestId, checkIn, checkOut);
		final long end = System.nanoTime();
		System.out.println("[METRICS] createReservation took " + (end - start) + " ns");
	}
}
