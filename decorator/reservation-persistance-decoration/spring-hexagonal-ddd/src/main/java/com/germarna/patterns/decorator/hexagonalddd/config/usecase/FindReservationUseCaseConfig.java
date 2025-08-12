package com.germarna.patterns.decorator.hexagonalddd.config.usecase;

import com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.findreservation.TransactionalFindReservationUseCaseDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.service.FindReservationService;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.FindReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class FindReservationUseCaseConfig {

	@Bean
	public FindReservationUseCase coreFindReservationUseCase(FindReservationPort findReservationPort) {
		return new FindReservationService(findReservationPort);
	}

	@Bean
	@Primary
	public FindReservationUseCase transactionalFindReservationUseCase(
			@Qualifier("coreFindReservationUseCase") FindReservationUseCase coreFindReservationService) {
		return new TransactionalFindReservationUseCaseDecorator(coreFindReservationService);
	}
}
