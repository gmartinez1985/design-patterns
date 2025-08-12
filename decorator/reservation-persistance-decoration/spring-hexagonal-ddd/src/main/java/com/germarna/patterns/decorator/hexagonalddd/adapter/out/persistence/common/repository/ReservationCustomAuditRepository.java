package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationActionType;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationAuditEntity;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class ReservationCustomAuditRepository {
	private final EntityManager entityManager;

	public void saveAuditEntry(Reservation reservation, ReservationActionType actionType) {
		final ReservationAuditEntity audit = ReservationAuditEntity.builder().actionType(actionType)
				.reservationId(reservation.getReservationId()).checkInDate(reservation.getCheckInDate())
				.checkOutDate(reservation.getCheckOutDate()).roomId(reservation.getRoomId())
				.guestId(reservation.getGuestId()).auditTimestamp(LocalDateTime.now()).build();

		this.entityManager.persist(audit);
	}
}
