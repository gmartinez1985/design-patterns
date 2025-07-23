package com.example.reservation.observer;

import com.example.reservation.domain.model.Reservation;
import com.example.reservation.domain.service.ReservationService;

public class ReservationCreatedSmsObserver implements Observer {
	private final ReservationService subject;

	public ReservationCreatedSmsObserver(ReservationService subject) {
		this.subject = subject;
	}

	public void update() {
		final Reservation r = this.subject.getLatestReservation();
		System.out.println("[SMS] Confirmation sent to " + r.getGuestName() + " for room type: " + r.getRoomType());
	}
}
