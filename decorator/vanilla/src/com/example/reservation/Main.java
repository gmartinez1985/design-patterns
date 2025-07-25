package com.example.reservation;

import com.example.reservation.decorators.AuthorizationReservationService;
import com.example.reservation.decorators.LoggingReservationService;
import com.example.reservation.decorators.MetricsReservationService;
import com.example.reservation.decorators.ValidationReservationService;
import com.example.reservation.domain.service.CoreReservationService;
import com.example.reservation.domain.service.ReservationService;

import java.util.Date;
import java.util.UUID;

public class Main {
	public static void main(String[] args) {
		final UUID roomId = UUID.randomUUID();
		final UUID guestId = UUID.randomUUID();
		final Date checkIn = new Date(); // today
		final Date checkOut = new Date(checkIn.getTime() + 2 * 24 * 60 * 60 * 1000); // +2 days

		// Compose decorators
		final ReservationService service = new AuthorizationReservationService(new MetricsReservationService(
				new ValidationReservationService(new LoggingReservationService(new CoreReservationService()))));

		// Call service
		service.createReservation(roomId, guestId, checkIn, checkOut);
	}

}
