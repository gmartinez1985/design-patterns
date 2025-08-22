package com.germarna.patterns.decorator.hexagonalddd.application.domain.service;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.usecase.CreateReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CreateReservationService implements CreateReservationUseCase {

	private final PaymentClient paymentClient;

	public CreateReservationService(PaymentClient paymentClient) {
		this.paymentClient = paymentClient;
	}

	@Override
	public boolean createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut, UUID reservationId) {
		boolean paymentSuccessful = false;
		final double totalAmount = this.calculateTotalAmount(roomId, checkIn, checkOut); // implement this method
		final Reservation reservation = new Reservation(reservationId, roomId, guestId, checkIn, checkOut, totalAmount);

		// Process payment
		paymentSuccessful = this.paymentClient.pay(reservation.getReservationId(), reservation.getTotalAmount());
		if (!paymentSuccessful) {
			throw new RuntimeException("Payment failed. Reservation not created.");
		}

		System.out.println("\tReservation created with ID: " + reservation.getReservationId());
		return true;
	}

	private double calculateTotalAmount(UUID roomId, Date checkIn, Date checkOut) {
		// Dummy example calculation (you can replace with real logic)
		final long diffInMillies = checkOut.getTime() - checkIn.getTime();
		final long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		final double roomRate = 100.0; // example fixed rate
		return diffInDays * roomRate;
	}
}
