package com.germarna.patterns.decorator.hexagonalddd.config.usecase;
import com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.changedatesofreservation.TransactionalChangeDatesOfReservationUseCaseDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.service.ChangeDatesOfReservationService;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.ChangeDatesOfReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.UpdateReservationPort;
import com.germarna.patterns.decorator.hexagonalddd.config.helpers.DecoratorTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "app.startup-runner.enabled=false")
class ChangeDatesOfReservationUseCaseConfigTest {

	@Autowired
	private ChangeDatesOfReservationUseCase changeDatesOfReservationUseCase;

	@MockitoBean
	private FindReservationPort findReservationPort;

	@MockitoBean
	private UpdateReservationPort updateReservationPort;

	@Test
	void testDecoratorsWiringOrder() throws Exception {
		final var transactional = this.changeDatesOfReservationUseCase;
		assertThat(transactional).isInstanceOf(TransactionalChangeDatesOfReservationUseCaseDecorator.class);

		final var service = DecoratorTestUtils.getDelegate(transactional);
		assertThat(service).isInstanceOf(ChangeDatesOfReservationService.class);

		assertThrows(NoSuchFieldException.class, () -> DecoratorTestUtils.getDelegate(service));
	}
}
