package com.germarna.patterns.decorator.hexagonalddd.application.port.in;

import java.util.Date;
import java.util.UUID;

public interface SaveReservationUseCase {
	UUID createReservation(UUID roomId, UUID guestId, Date checkIn, Date checkOut);
}
