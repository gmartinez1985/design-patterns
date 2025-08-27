package com.germarna.patterns.decorator.hexagonalddd.config.persistence;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationCustomAuditRepository;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.savereservation.SaveReservationPersistenceAdapter;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.savereservation.decorator.AuditSaveReservationPersistenceAdapterDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveReservationPort;
import com.germarna.patterns.decorator.hexagonalddd.config.helpers.DecoratorTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "app.startup-runner.enabled=false")
class SaveReservationPersistenceConfigTest {

	@Autowired
	private SaveReservationPort saveReservationPort;

	@MockitoBean
	private ReservationCustomAuditRepository auditRepository;

	@Test
	void testDecoratorsWiringOrder() throws Exception {
		final var audited = this.saveReservationPort;
		assertThat(audited).isInstanceOf(AuditSaveReservationPersistenceAdapterDecorator.class);

		final var core = DecoratorTestUtils.getDelegate(audited);
		assertThat(core).isInstanceOf(SaveReservationPersistenceAdapter.class);

		assertThrows(NoSuchFieldException.class, () -> DecoratorTestUtils.getDelegate(core));
	}
}
