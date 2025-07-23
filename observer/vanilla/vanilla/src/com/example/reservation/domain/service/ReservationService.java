package com.example.reservation.domain.service;

import com.example.reservation.domain.model.Reservation;
import com.example.reservation.observer.Observer;
import com.example.reservation.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class ReservationService implements Subject {
	private final List<Observer> observers = new ArrayList<>();
	private Reservation latestReservation;

	public void attach(Observer o) {
        this.observers.add(o);
	}

	public void detach(Observer o) {
        this.observers.remove(o);
	}

	public void notifyObservers() {
		for (final Observer o : this.observers) {
			o.update();
		}
	}

	public void createReservation(String guestName, String roomType) {
		this.latestReservation = new Reservation(guestName, roomType);
		System.out.println("Reservation " + this.latestReservation.getReservationId() + " created for " + guestName);
        this.notifyObservers();
	}

	public Reservation getLatestReservation() {
		return this.latestReservation;
	}
}
