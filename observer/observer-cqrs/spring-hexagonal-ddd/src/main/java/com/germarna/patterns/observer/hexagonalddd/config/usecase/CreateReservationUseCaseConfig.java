package com.germarna.patterns.observer.hexagonalddd.config.usecase;

import com.germarna.patterns.observer.hexagonalddd.adapter.in.usecase.decorator.savereservation.TransactionalCreateReservationUseCaseDecorator;
import com.germarna.patterns.observer.hexagonalddd.application.domain.service.CreateReservationService;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase.CreateReservationUseCase;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation.SaveReservationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class CreateReservationUseCaseConfig {
	@Bean
	public CreateReservationUseCase coreSaveReservationUseCase(SaveReservationPort saveReservationPort) {
		return new CreateReservationService(saveReservationPort);
	}

	@Bean
	@Primary
	public CreateReservationUseCase transactionalSaveReservationUseCase(
			@Qualifier("coreSaveReservationUseCase") CreateReservationUseCase core) {
		return new TransactionalCreateReservationUseCaseDecorator(core);
	}
}
