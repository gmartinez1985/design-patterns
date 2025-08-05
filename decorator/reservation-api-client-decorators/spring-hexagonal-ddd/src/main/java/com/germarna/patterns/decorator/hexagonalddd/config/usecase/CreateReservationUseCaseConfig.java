package com.germarna.patterns.decorator.hexagonalddd.config.usecase;

import com.germarna.patterns.decorator.hexagonalddd.adapter.in.decorator.usecase.LoggingCreateReservationServiceDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.service.CreateReservationService;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.usecase.CreateReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class CreateReservationUseCaseConfig {

	private final PaymentClient paymentClient;

	@Bean
	public CreateReservationUseCase createReservationUseCase() {
		return new CreateReservationService(this.paymentClient);
	}

	@Bean
	@Primary
	public CreateReservationUseCase loggingDecorator(
			@Qualifier("createReservationUseCase") CreateReservationUseCase logging) {
		return new LoggingCreateReservationServiceDecorator(logging);
	}
}
