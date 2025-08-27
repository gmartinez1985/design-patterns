package com.germarna.patterns.decorator.hexagonalddd.config.usecase;
import com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.findreservation.TransactionalFindReservationUseCaseDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.service.FindReservationService;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.FindReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;
import com.germarna.patterns.decorator.hexagonalddd.config.helpers.DecoratorTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "app.startup-runner.enabled=false")
class FindReservationUseCaseConfigTest {

	@Autowired
	private FindReservationUseCase findReservationUseCase;

	@MockitoBean
	private FindReservationPort findReservationPort;

	@Test
	void testDecoratorsWiringOrder() throws Exception {
		final var transactional = this.findReservationUseCase;
		assertThat(transactional).isInstanceOf(TransactionalFindReservationUseCaseDecorator.class);

		final var service = DecoratorTestUtils.getDelegate(transactional);
		assertThat(service).isInstanceOf(FindReservationService.class);

		assertThrows(NoSuchFieldException.class, () -> DecoratorTestUtils.getDelegate(service));
	}
}