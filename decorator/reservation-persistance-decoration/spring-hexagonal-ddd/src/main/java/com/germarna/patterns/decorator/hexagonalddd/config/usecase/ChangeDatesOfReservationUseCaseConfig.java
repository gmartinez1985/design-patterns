package com.germarna.patterns.decorator.hexagonalddd.config.usecase;

import com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.changedatesofreservation.TransactionalChangeDatesOfReservationUseCaseDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.service.ChangeDatesOfReservationService;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.ChangeDatesOfReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.UpdateReservationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class ChangeDatesOfReservationUseCaseConfig {

	@Bean
	public ChangeDatesOfReservationUseCase coreChangeDatesOfReservationUseCase(FindReservationPort findReservationPort,
			UpdateReservationPort updateReservationPort) {
		return new ChangeDatesOfReservationService(findReservationPort, updateReservationPort);
	}

	@Bean
	@Primary
	public ChangeDatesOfReservationUseCase transactionalChangeDatesOfReservationUseCase(
			@Qualifier("coreChangeDatesOfReservationUseCase") ChangeDatesOfReservationUseCase changeDatesOfReservationUseCase) {
		return new TransactionalChangeDatesOfReservationUseCaseDecorator(changeDatesOfReservationUseCase);
	}
}
