package com.example.reservation.domain.model.decorators;

import com.example.reservation.domain.model.ReservationComponent;

public class BreakfastDecorator extends BaseReservationDecorator {

	public BreakfastDecorator(ReservationComponent reservation) {
		super(reservation);
	}

	@Override
	public double getCost() {
		return super.getCost() + 15.0;
	}

	@Override
	public String getDescription() {
		return super.getDescription() + ", with Breakfast";
	}
}
