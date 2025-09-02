package com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase;

import java.util.Date;
import java.util.UUID;

public interface CreateReservationUseCase {
	UUID createReservation(UUID roomId, String guestName, String guestEmail, Date checkIn, Date checkOut);
}
