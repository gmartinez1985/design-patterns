package com.germarna.patterns.observer.hexagonalddd.application.domain.service;

import com.germarna.patterns.observer.hexagonalddd.application.port.in.CreateReservationUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateReservationServiceTest {

	@Test
	@DisplayName("CreateReservationService returns a non-null reservation ID")
	void testCreateReservation() {
		final CreateReservationUseCase service = new CreateReservationService();
		final String guestName = "John Doe";
		final String roomType = "Deluxe";

		final UUID reservationId = service.createReservation(guestName, roomType);

		assertNotNull(reservationId);
	}
}
