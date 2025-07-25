package com.example.reservation.decorators;

import com.example.reservation.domain.service.ReservationService;

import java.util.Date;
import java.util.UUID;

public class MetricsReservationService implements ReservationService {

	private final ReservationService delegate;

	public MetricsReservationService(ReservationService delegate) {
		this.delegate = delegate;
	}

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		final long start = System.nanoTime();
		this.delegate.createReservation(roomId, guestId, checkIn, checkOut);
		final long end = System.nanoTime();
		System.out.println("[METRICS] createReservation took " + (end - start) + " ns");
	}
}
