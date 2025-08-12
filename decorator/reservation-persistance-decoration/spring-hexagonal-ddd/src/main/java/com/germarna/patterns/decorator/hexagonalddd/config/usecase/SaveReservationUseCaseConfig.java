package com.germarna.patterns.decorator.hexagonalddd.config.usecase;

import com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.savereservation.TransactionalSaveReservationUseCaseDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.service.SaveReservationService;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.SaveReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveReservationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class SaveReservationUseCaseConfig {

	@Bean
	public SaveReservationUseCase coreSaveReservationUseCase(SaveReservationPort saveReservationPort) {
		return new SaveReservationService(saveReservationPort);
	}

	@Bean
	@Primary
	public SaveReservationUseCase transactionalSaveReservationUseCase(
			@Qualifier("coreSaveReservationUseCase") SaveReservationUseCase coreCreateReservationService) {
		return new TransactionalSaveReservationUseCaseDecorator(coreCreateReservationService);
	}
}
