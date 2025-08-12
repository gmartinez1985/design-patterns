package com.germarna.patterns.decorator.hexagonalddd.config.persistence;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationCustomAuditRepository;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.findreservation.decorator.AuditFindReservationPersistenceAdapterDecorator;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.findreservation.decorator.CachedFindReservationPersistenceAdapterDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class FindReservationPersistenceConfig {

	@Bean
	FindReservationPort cachedFindReservationPort(
			@Qualifier("findReservationPersistenceAdapter") FindReservationPort delegate) {
		return new CachedFindReservationPersistenceAdapterDecorator(delegate);
	}
	@Bean
	@Primary
	public FindReservationPort auditedFindReservationPort(
			@Qualifier("cachedFindReservationPort") FindReservationPort delegate,
			ReservationCustomAuditRepository auditRepository) {
		return new AuditFindReservationPersistenceAdapterDecorator(delegate, auditRepository);
	}
}
