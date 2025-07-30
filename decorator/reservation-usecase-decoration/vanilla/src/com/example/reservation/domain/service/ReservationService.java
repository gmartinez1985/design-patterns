package com.example.reservation.domain.service;

import java.util.Date;
import java.util.UUID;

public interface ReservationService {
	void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut);
}
