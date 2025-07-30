package com.example.reservation.decorators;

import com.example.reservation.domain.service.ReservationService;

import java.util.Date;
import java.util.UUID;

public class LoggingReservationServiceDecorator extends BaseReservationDecorator {

	public LoggingReservationServiceDecorator(ReservationService delegate) {
		super(delegate);
	}

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		System.out.println("[LOG] Creating reservation for guest: " + guestId);
		super.createReservation(roomId, guestId, checkIn, checkOut);
		System.out.println("[LOG] Reservation created");
	}
}
