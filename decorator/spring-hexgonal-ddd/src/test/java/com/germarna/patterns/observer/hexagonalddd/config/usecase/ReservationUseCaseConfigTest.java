package com.germarna.patterns.observer.hexagonalddd.config.usecase;

import com.germarna.patterns.observer.hexagonalddd.domain.port.in.usecase.CreateReservationUseCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReservationUseCaseConfigTest {

	@Test
	void createReservationUseCase_doesNotThrow() {
		final ReservationUseCaseConfig config = new ReservationUseCaseConfig();
		final CreateReservationUseCase useCase = config.createReservationUseCase();
		assertNotNull(useCase);
	}
}
