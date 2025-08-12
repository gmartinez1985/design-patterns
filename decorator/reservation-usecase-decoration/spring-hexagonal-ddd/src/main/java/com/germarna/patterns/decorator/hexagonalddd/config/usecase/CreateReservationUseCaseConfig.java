package com.germarna.patterns.decorator.hexagonalddd.config.usecase;

import com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.*;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.service.CreateReservationService;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.CreateReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveDummyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class CreateReservationUseCaseConfig {
	private final SaveDummyPort saveDummyPort;

	@Bean
	public CreateReservationUseCase coreCreateReservationUseCase() {
		return new CreateReservationService(this.saveDummyPort);
	}

	@Bean
	public CreateReservationUseCase transactionalCreateReservationUseCase(
			@Qualifier("coreCreateReservationUseCase") CreateReservationUseCase core) {
		return new TransactionalCreateReservationUseCaseDecorator(core);
	}

	@Bean
	public CreateReservationUseCase loggingCreateReservationUseCase(
			@Qualifier("transactionalCreateReservationUseCase") CreateReservationUseCase transactional) {
		return new LoggingCreateReservationUseCaseDecorator(transactional);
	}

	@Bean
	public CreateReservationUseCase metricsCreateReservationUseCase(
			@Qualifier("loggingCreateReservationUseCase") CreateReservationUseCase logging) {
		return new MetricsCreateReservationUseCaseDecorator(logging);
	}

	@Bean
	public CreateReservationUseCase validationCreateReservationUseCase(
			@Qualifier("metricsCreateReservationUseCase") CreateReservationUseCase metrics) {
		return new ValidationCreateReservationUseCaseDecorator(metrics);
	}

	@Bean
	@Primary
	public CreateReservationUseCase authorizationCreateReservationUseCase(
			@Qualifier("validationCreateReservationUseCase") CreateReservationUseCase validation) {
		return new AuthorizationCreateReservationUseCaseDecorator(validation);
	}
}
