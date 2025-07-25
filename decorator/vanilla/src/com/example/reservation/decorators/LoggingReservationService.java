package com.example.reservation.decorators;

import com.example.reservation.domain.service.ReservationService;

import java.util.Date;
import java.util.UUID;

public class LoggingReservationService implements ReservationService {

	private final ReservationService delegate;

	public LoggingReservationService(ReservationService delegate) {
		this.delegate = delegate;
	}

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		System.out.println("[LOG] Creating reservation for guest: " + guestId);
		this.delegate.createReservation(roomId, guestId, checkIn, checkOut);
		System.out.println("[LOG] Reservation created");
	}
}
