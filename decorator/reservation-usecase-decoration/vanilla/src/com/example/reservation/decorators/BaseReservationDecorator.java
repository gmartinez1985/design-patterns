package com.example.reservation.decorators;

import com.example.reservation.domain.service.ReservationService;

import java.util.Date;
import java.util.UUID;

public abstract class BaseReservationDecorator implements ReservationService {
	protected final ReservationService delegate;

	protected BaseReservationDecorator(ReservationService delegate) {
		this.delegate = delegate;
	}

	@Override
	public void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut) {
		this.delegate.createReservation(roomId, guestId, checkIn, checkOut);
	}
}
