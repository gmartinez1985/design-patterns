package com.germarna.patterns.decorator.hexagonalddd.config.persistence;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationCustomAuditRepository;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.findreservation.FindReservationPersistenceAdapter;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.findreservation.decorator.AuditFindReservationPersistenceAdapterDecorator;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.findreservation.decorator.CachedFindReservationPersistenceAdapterDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;
import com.germarna.patterns.decorator.hexagonalddd.config.helpers.DecoratorTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "app.startup-runner.enabled=false")
class FindReservationPersistenceConfigTest {

	@Autowired
	private FindReservationPort findReservationPort;

	@MockitoBean
	private ReservationCustomAuditRepository auditRepository;

	@Test
	void testDecoratorsWiringOrder() throws Exception {
		final var audited = this.findReservationPort;
		assertThat(audited).isInstanceOf(AuditFindReservationPersistenceAdapterDecorator.class);

		final var cached = DecoratorTestUtils.getDelegate(audited);
		assertThat(cached).isInstanceOf(CachedFindReservationPersistenceAdapterDecorator.class);

		final var core = DecoratorTestUtils.getDelegate(cached);
		assertThat(core).isInstanceOf(FindReservationPersistenceAdapter.class);

		assertThrows(NoSuchFieldException.class, () -> DecoratorTestUtils.getDelegate(core));
	}
}
