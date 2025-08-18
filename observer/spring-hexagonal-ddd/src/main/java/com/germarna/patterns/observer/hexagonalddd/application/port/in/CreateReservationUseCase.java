package com.germarna.patterns.observer.hexagonalddd.application.port.in;

import java.util.UUID;

public interface CreateReservationUseCase {
	UUID createReservation(String guestName, String roomType);
}
