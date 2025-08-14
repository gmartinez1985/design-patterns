package com.germarna.patterns.observer.hexagonalddd.application.port.in;

public interface CreateReservationUseCase {
	void createReservation(String guestName, String roomType);
}
