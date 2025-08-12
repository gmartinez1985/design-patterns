package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.savereservation.decorator;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationActionType;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationCustomAuditRepository;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveReservationPort;

import java.util.UUID;

public class AuditSaveReservationPersistenceDecorator extends BaseSaveReservationPersistenceDecorator {

	private final ReservationCustomAuditRepository auditRepository;

	public AuditSaveReservationPersistenceDecorator(SaveReservationPort delegate,
			ReservationCustomAuditRepository auditRepository) {
		super(delegate);
		this.auditRepository = auditRepository;
	}

	@Override
	public UUID saveReservation(Reservation reservation) {
		System.out.println("[AUDIT] Auditing saveReservation");
		this.auditRepository.saveAuditEntry(reservation, ReservationActionType.CREATE);
		return super.saveReservation(reservation);
	}
}
