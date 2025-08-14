package com.example.reservation.domain.model;

import java.util.UUID;

public interface ReservationComponent {
	UUID getReservationId();
	double getCost();
	String getDescription();
}
