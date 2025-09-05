package com.germarna.patterns.observer.hexagonalddd.config.usecase;

import com.germarna.patterns.observer.hexagonalddd.application.domain.service.FindReservationService;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase.FindReservationUseCase;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation.FindReservationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FindReservationUseCaseConfig {
	@Bean
	public FindReservationUseCase coreFindReservationUseCase(FindReservationPort findReservationPort) {
		return new FindReservationService(findReservationPort);
	}
}
