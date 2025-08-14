package com.example.reservation;

import com.example.reservation.domain.model.BasicReservation;
import com.example.reservation.domain.model.ReservationComponent;
import com.example.reservation.domain.model.decorators.AirportPickupDecorator;
import com.example.reservation.domain.model.decorators.BreakfastDecorator;

import java.util.Date;
import java.util.UUID;

public class Main {
	public static void main(String[] args) {
		ReservationComponent reservation = new BasicReservation(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
				new Date(), new Date());

		System.out.println(reservation.getDescription() + " costs $" + reservation.getCost());

		reservation = new BreakfastDecorator(reservation);
		System.out.println(reservation.getDescription() + " costs $" + reservation.getCost());

		reservation = new AirportPickupDecorator(reservation);
		System.out.println(reservation.getDescription() + " costs $" + reservation.getCost());
	}
}
