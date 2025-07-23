package com.example.reservation;

import com.example.reservation.domain.service.ReservationService;
import com.example.reservation.observer.ReservationCreatedEmailObserver;
import com.example.reservation.observer.ReservationCreatedSlackObserver;
import com.example.reservation.observer.ReservationCreatedSmsObserver;

public class SimpleObserverGoFStyle {
	public static void main(String[] args) {
		final ReservationService service = new ReservationService();

		service.attach(new ReservationCreatedEmailObserver(service));
		service.attach(new ReservationCreatedSlackObserver(service));
		service.attach(new ReservationCreatedSmsObserver(service));

		service.createReservation("John Doe", "Deluxe Suite");
		System.out.println("---");
		service.createReservation("Jane Doe", "Standard Room");
	}
}
