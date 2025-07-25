package com.example.reservation;

import com.example.reservation.decorators.AuthorizationReservationServiceDecorator;
import com.example.reservation.decorators.LoggingReservationServiceDecorator;
import com.example.reservation.decorators.MetricsReservationServiceDecorator;
import com.example.reservation.decorators.ValidationReservationServiceDecorator;
import com.example.reservation.domain.service.ReservationService;
import com.example.reservation.domain.service.ReservationServiceImpl;

import java.util.Date;
import java.util.UUID;

public class Main {
	public static void main(String[] args) {
		final UUID roomId = UUID.randomUUID();
		final UUID guestId = UUID.randomUUID();
		final Date checkIn = new Date(); // today
		final Date checkOut = new Date(checkIn.getTime() + 2 * 24 * 60 * 60 * 1000); // +2 days

		// Compose decorators
		final ReservationService service = new AuthorizationReservationServiceDecorator(
				new MetricsReservationServiceDecorator(new ValidationReservationServiceDecorator(
						new LoggingReservationServiceDecorator(new ReservationServiceImpl()))));

		// Call service
		service.createReservation(roomId, guestId, checkIn, checkOut);
	}
}
