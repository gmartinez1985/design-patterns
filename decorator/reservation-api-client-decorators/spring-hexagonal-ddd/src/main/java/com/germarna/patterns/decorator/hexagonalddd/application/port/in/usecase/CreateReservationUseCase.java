package com.germarna.patterns.decorator.hexagonalddd.application.port.in.usecase;

import java.util.Date;
import java.util.UUID;

public interface CreateReservationUseCase {
	void createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut, UUID reservationId);
}
