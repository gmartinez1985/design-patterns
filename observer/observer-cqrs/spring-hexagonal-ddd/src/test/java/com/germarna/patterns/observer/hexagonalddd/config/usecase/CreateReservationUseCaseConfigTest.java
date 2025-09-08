package com.germarna.patterns.observer.hexagonalddd.config.usecase;

import com.germarna.patterns.observer.hexagonalddd.adapter.in.usecase.decorator.savereservation.TransactionalCreateReservationUseCaseDecorator;
import com.germarna.patterns.observer.hexagonalddd.application.domain.service.CreateReservationService;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase.CreateReservationUseCase;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation.SaveReservationPort;
import com.germarna.patterns.observer.hexagonalddd.config.helpers.DecoratorTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "app.startup-runner.enabled=false", classes = {CreateReservationUseCaseConfig.class})
class CreateReservationUseCaseConfigTest {

	@Autowired
	private CreateReservationUseCase createReservationUseCase;

	@MockitoBean
	private SaveReservationPort saveReservationPort;

	@Autowired
	ApplicationContext ctx;

	@Test
	void testDecoratorsWiringOrder() throws Exception {
		final var transactional = this.createReservationUseCase;
		assertThat(transactional).isInstanceOf(TransactionalCreateReservationUseCaseDecorator.class);

		final var service = DecoratorTestUtils.getDelegate(transactional);
		assertThat(service).isInstanceOf(CreateReservationService.class);

		assertThrows(NoSuchFieldException.class, () -> DecoratorTestUtils.getDelegate(service));
	}
}
