package com.germarna.patterns.observer.hexagonalddd.config.usecase;

import com.germarna.patterns.observer.hexagonalddd.application.domain.service.FindReservationService;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase.FindReservationUseCase;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation.FindReservationPort;
import com.germarna.patterns.observer.hexagonalddd.config.helpers.DecoratorTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "app.startup-runner.enabled=false", classes = {FindReservationUseCaseConfig.class})
class FindReservationUseCaseConfigTest {

	@Autowired
	private FindReservationUseCase findReservationUseCase;

	@MockitoBean
	private FindReservationPort findReservationPort;

	@Test
	void testWiringIsDirectServiceWithoutDecorator() {
		final var bean = this.findReservationUseCase;
		assertThat(bean).isInstanceOf(FindReservationService.class);

		assertThrows(NoSuchFieldException.class, () -> DecoratorTestUtils.getDelegate(bean));
	}
}
