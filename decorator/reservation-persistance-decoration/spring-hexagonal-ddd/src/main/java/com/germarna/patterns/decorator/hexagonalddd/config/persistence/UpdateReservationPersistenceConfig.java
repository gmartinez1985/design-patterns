package com.germarna.patterns.decorator.hexagonalddd.config.persistence;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationCustomAuditRepository;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.updatereservation.decorator.AuditUpdateReservationPersistenceDecorator;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.updatereservation.decorator.CachedUpdateReservationPersistenceDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.UpdateReservationPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class UpdateReservationPersistenceConfig {

	@Bean
	public UpdateReservationPort cachedUpdateReservationPort(
			@Qualifier("updateReservationPersistenceAdapter") UpdateReservationPort delegate) {
		return new CachedUpdateReservationPersistenceDecorator(delegate);
	}

	@Bean
	@Primary
	public UpdateReservationPort auditedUpdateReservationPort(
			@Qualifier("cachedUpdateReservationPort") UpdateReservationPort delegate,
			ReservationCustomAuditRepository auditRepository) {
		return new AuditUpdateReservationPersistenceDecorator(delegate, auditRepository);
	}
}
