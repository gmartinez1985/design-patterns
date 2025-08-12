package com.germarna.patterns.decorator.hexagonalddd.config.persistence;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationCustomAuditRepository;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.savereservation.decorator.AuditSaveReservationPersistenceDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveReservationPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SaveReservationPersistenceConfig {
	@Bean
	@Primary
	public SaveReservationPort cachedSaveReservationPort(
			@Qualifier("saveReservationPersistenceAdapter") SaveReservationPort delegate,
			ReservationCustomAuditRepository auditRepository) {
		return new AuditSaveReservationPersistenceDecorator(delegate, auditRepository);
	}
}
