package com.germarna.patterns.decorator.hexagonalddd.config.usecase;

import com.germarna.patterns.decorator.hexagonalddd.adapter.in.decorator.*;
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

	@Bean("coreCreateReservationService")
	public CreateReservationUseCase coreCreateReservationService() {
		return new CreateReservationService(this.saveDummyPort);
	}

	@Bean("transactionalDecorator")
	public CreateReservationUseCase transactionalDecorator(
			@Qualifier("coreCreateReservationService") CreateReservationUseCase core) {
		return new TransactionalCreateReservationDecorator(core);
	}

	@Bean("loggingDecorator")
	public CreateReservationUseCase loggingDecorator(
			@Qualifier("transactionalDecorator") CreateReservationUseCase transactional) {
		return new LoggingCreateReservationServiceDecorator(transactional);
	}

	@Bean("metricsDecorator")
	public CreateReservationUseCase metricsDecorator(@Qualifier("loggingDecorator") CreateReservationUseCase logging) {
		return new MetricsCreateReservationServiceDecorator(logging);
	}

	@Bean("validationDecorator")
	public CreateReservationUseCase validationDecorator(
			@Qualifier("metricsDecorator") CreateReservationUseCase metrics) {
		return new ValidationCreateReservationServiceDecorator(metrics);
	}

	@Bean
	@Primary
	public CreateReservationUseCase authorizationDecorator(
			@Qualifier("validationDecorator") CreateReservationUseCase validation) {
		return new AuthorizationCreateReservationServiceDecorator(validation);
	}
}
