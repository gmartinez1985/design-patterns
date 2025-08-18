package com.germarna.patterns.observer.hexagonalddd.config.usecase;

import com.germarna.patterns.observer.hexagonalddd.application.port.in.CreateReservationUseCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateReservationUseCaseConfigTest {

	@Test
	void coreCreateReservationUseCaseShouldReturnNonNullBean() {
		final CreateReservationUseCaseConfig config = new CreateReservationUseCaseConfig();
		final CreateReservationUseCase useCase = config.coreCreateReservationUseCase();
		assertNotNull(useCase, "coreCreateReservationUseCase should not return null");
	}
}
