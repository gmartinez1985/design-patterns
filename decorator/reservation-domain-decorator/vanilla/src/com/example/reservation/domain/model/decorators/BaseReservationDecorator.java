package com.example.reservation.domain.model.decorators;

import com.example.reservation.domain.model.ReservationComponent;

import java.util.UUID;

public abstract class BaseReservationDecorator implements ReservationComponent {
	protected ReservationComponent delegate;

	public BaseReservationDecorator(ReservationComponent delegate) {
		this.delegate = delegate;
	}

	@Override
	public UUID getReservationId() {
		return this.delegate.getReservationId();
	}

	@Override
	public double getCost() {
		return this.delegate.getCost();
	}

	@Override
	public String getDescription() {
		return this.delegate.getDescription();
	}
}
