package com.germarna.patterns.decorator.hexagonalddd.config.usecase;

import com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.LoggingCreateReservationUseCaseDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.service.CreateReservationService;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.usecase.CreateReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import com.germarna.patterns.decorator.hexagonalddd.testutil.DecoratorTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CreateReservationUseCaseConfigTest {

	@Autowired
	private CreateReservationUseCase createReservationUseCase;

	@MockitoBean
	private PaymentClient paymentClient; // Mockeado para que no llame al mundo real

	@MockitoBean
	private CommandLineRunner startupRunner; // Mockeado para que no arruine el contexto

	@Test
	void testDecoratorsWiringOrder() throws Exception {
		final var logging = this.createReservationUseCase;
		assertThat(logging).isInstanceOf(LoggingCreateReservationUseCaseDecorator.class);

		final var service = DecoratorTestUtils.getDelegate(logging);
		assertThat(service).isInstanceOf(CreateReservationService.class);

		assertThrows(NoSuchFieldException.class, () -> DecoratorTestUtils.getDelegate(service));
	}
}
