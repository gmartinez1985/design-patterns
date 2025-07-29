package com.germarna.patterns.decorator.hexagonalddd.integration;

import com.germarna.patterns.decorator.hexagonalddd.domain.port.in.usecase.CreateReservationUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class CreateReservationIT {

	@Autowired
	private CreateReservationUseCase createReservationUseCase;

	@Test
	void createReservation_executesThroughAllDecorators_withoutExceptions() {
		final UUID roomId = UUID.randomUUID();
		final UUID guestId = UUID.randomUUID();
		final Date checkIn = new Date();
		final Date checkOut = new Date(checkIn.getTime() + 2 * 24 * 60 * 60 * 1000);

		assertDoesNotThrow(() -> this.createReservationUseCase.createReservation(roomId, guestId, checkIn, checkOut));
	}
}
