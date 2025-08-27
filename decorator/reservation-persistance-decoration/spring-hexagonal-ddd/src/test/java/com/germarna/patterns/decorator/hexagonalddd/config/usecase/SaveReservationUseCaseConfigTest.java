package com.germarna.patterns.decorator.hexagonalddd.config.usecase;
import com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.savereservation.TransactionalSaveReservationUseCaseDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.service.SaveReservationService;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.SaveReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveReservationPort;
import com.germarna.patterns.decorator.hexagonalddd.config.helpers.DecoratorTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "app.startup-runner.enabled=false")
class SaveReservationUseCaseConfigTest {

	@Autowired
	private SaveReservationUseCase saveReservationUseCase;

	@MockitoBean
	private SaveReservationPort saveReservationPort;

	@Test
	void testDecoratorsWiringOrder() throws Exception {
		final var transactional = this.saveReservationUseCase;
		assertThat(transactional).isInstanceOf(TransactionalSaveReservationUseCaseDecorator.class);

		final var service = DecoratorTestUtils.getDelegate(transactional);
		assertThat(service).isInstanceOf(SaveReservationService.class);

		assertThrows(NoSuchFieldException.class, () -> DecoratorTestUtils.getDelegate(service));
	}
}
