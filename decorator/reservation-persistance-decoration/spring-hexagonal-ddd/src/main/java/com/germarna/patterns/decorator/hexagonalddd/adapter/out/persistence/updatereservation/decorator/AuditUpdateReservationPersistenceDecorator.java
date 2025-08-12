package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.updatereservation.decorator;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationActionType;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationCustomAuditRepository;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.UpdateReservationPort;

public class AuditUpdateReservationPersistenceDecorator extends BaseUpdateReservationPersistenceDecorator {

	private final ReservationCustomAuditRepository auditRepository;

	public AuditUpdateReservationPersistenceDecorator(UpdateReservationPort delegate,
			ReservationCustomAuditRepository auditRepository) {
		super(delegate);
		this.auditRepository = auditRepository;
	}

	@Override
	public Reservation updateReservation(Reservation reservation) {
		System.out.println("[AUDIT] Auditing updateReservation");
		this.auditRepository.saveAuditEntry(reservation, ReservationActionType.UPDATE);
		return super.updateReservation(reservation);
	}
}
