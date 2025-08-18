package com.germarna.patterns.observer.hexagonalddd.config.usecase;

import com.germarna.patterns.observer.hexagonalddd.application.domain.service.CreateReservationService;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.CreateReservationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateReservationUseCaseConfig {
	@Bean
	public CreateReservationUseCase coreCreateReservationUseCase() {
		return new CreateReservationService();
	}
}
