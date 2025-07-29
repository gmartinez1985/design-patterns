package com.germarna.patterns.decorator.hexagonalddd.config.usecase;

import com.germarna.patterns.decorator.hexagonalddd.application.decorator.*;
import com.germarna.patterns.decorator.hexagonalddd.config.support.UseCaseBuilder;
import com.germarna.patterns.decorator.hexagonalddd.domain.port.in.usecase.CreateReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.domain.service.CreateReservationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservationUseCaseConfig {

	@Bean
	public CreateReservationUseCase createReservationUseCase() {
		return UseCaseBuilder.wrap(this.coreService()).with(this::withTransaction).with(this::withMetrics)
				.with(this::withValidation).with(this::withAuthentication).with(this::withLogging).build();
	}

	private CreateReservationUseCase coreService() {
		return new CreateReservationService();
	}

	private CreateReservationUseCase withLogging(CreateReservationUseCase delegate) {
		return new LoggingCreateReservationServiceDecorator(delegate);
	}

	private CreateReservationUseCase withTransaction(CreateReservationUseCase delegate) {
		return new TransactionalCreateReservationDecorator(delegate);
	}

	private CreateReservationUseCase withMetrics(CreateReservationUseCase delegate) {
		return new MetricsCreateReservationServiceDecorator(delegate);
	}

	private CreateReservationUseCase withValidation(CreateReservationUseCase delegate) {
		return new ValidationCreateReservationServiceDecorator(delegate);
	}

	private CreateReservationUseCase withAuthentication(CreateReservationUseCase delegate) {
		return new AuthorizationCreateReservationServiceDecorator(delegate);
	}
}
