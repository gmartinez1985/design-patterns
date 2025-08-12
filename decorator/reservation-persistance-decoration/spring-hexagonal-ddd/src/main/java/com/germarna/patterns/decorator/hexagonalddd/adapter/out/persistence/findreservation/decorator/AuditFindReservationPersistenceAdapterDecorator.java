package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.findreservation.decorator;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationActionType;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationCustomAuditRepository;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;

import java.util.UUID;

public class AuditFindReservationPersistenceAdapterDecorator extends BaseFindReservationPersistenceAdapterDecorator {

	private final ReservationCustomAuditRepository auditRepository;

	public AuditFindReservationPersistenceAdapterDecorator(FindReservationPort delegate,
			ReservationCustomAuditRepository auditRepository) {
		super(delegate);
		this.auditRepository = auditRepository;
	}

	@Override
	public Reservation loadReservation(UUID reservationId) {
		System.out.println("[AUDIT] Auditing loadReservation");
		final Reservation reservation = super.loadReservation(reservationId);
		this.auditRepository.saveAuditEntry(reservation, ReservationActionType.FIND);
		return reservation;
	}
}
