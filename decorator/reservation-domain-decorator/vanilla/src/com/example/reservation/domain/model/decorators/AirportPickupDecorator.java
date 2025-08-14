package com.example.reservation.domain.model.decorators;

import com.example.reservation.domain.model.ReservationComponent;

public class AirportPickupDecorator extends BaseReservationDecorator {

	public AirportPickupDecorator(ReservationComponent reservation) {
		super(reservation);
	}

	@Override
	public double getCost() {
		return super.getCost() + 40.0;
	}

	@Override
	public String getDescription() {
		return super.getDescription() + ", with Airport Pickup";
	}
}
