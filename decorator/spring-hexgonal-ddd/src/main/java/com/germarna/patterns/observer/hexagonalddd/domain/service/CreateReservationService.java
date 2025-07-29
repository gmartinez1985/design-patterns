package com.germarna.patterns.observer.hexagonalddd.domain.service;

import com.germarna.patterns.observer.hexagonalddd.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.domain.port.in.usecase.CreateReservationUseCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CreateReservationService implements CreateReservationUseCase {

	private final List<Reservation> reservations = new ArrayList<>();

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		final Reservation reservation = new Reservation(UUID.randomUUID(), roomId, guestId, checkIn, checkOut);

		this.reservations.add(reservation);

		System.out.println("\tReservation created with ID: " + reservation.getReservationId());
	}
}
