package com.germarna.patterns.decorator.hexagonalddd.config.persistence;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationCustomAuditRepository;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.updatereservation.UpdateReservationPersistenceAdapter;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.updatereservation.decorator.AuditUpdateReservationPersistenceDecorator;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.updatereservation.decorator.CachedUpdateReservationPersistenceDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.UpdateReservationPort;
import com.germarna.patterns.decorator.hexagonalddd.config.helpers.DecoratorTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "app.startup-runner.enabled=false")
class UpdateReservationPersistenceConfigTest {

	@Autowired
	private UpdateReservationPort updateReservationPort;

	@MockitoBean
	private ReservationCustomAuditRepository auditRepository;

	@Test
	void testDecoratorsWiringOrder() throws Exception {
		final var audited = this.updateReservationPort;
		assertThat(audited).isInstanceOf(AuditUpdateReservationPersistenceDecorator.class);

		final var cached = DecoratorTestUtils.getDelegate(audited);
		assertThat(cached).isInstanceOf(CachedUpdateReservationPersistenceDecorator.class);

		final var core = DecoratorTestUtils.getDelegate(cached);
		assertThat(core).isInstanceOf(UpdateReservationPersistenceAdapter.class);

		assertThrows(NoSuchFieldException.class, () -> DecoratorTestUtils.getDelegate(core));
	}
}
