package com.example.reservation.domain.service;

import com.example.reservation.domain.model.Reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ReservationServiceImpl implements ReservationService {

	private final List<Reservation> reservations = new ArrayList<>();

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		final Reservation reservation = new Reservation(UUID.randomUUID(), roomId, guestId, checkIn, checkOut);

		this.reservations.add(reservation);

		System.out.println("\tReservation created with ID: " + reservation.getReservationId());
	}
}
