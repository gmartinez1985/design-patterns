package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.findreservation;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.datamodel.ReservationJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.mapper.ReservationJpaMapper;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.ReservationJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation.FindReservationPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FindReservationPersistenceAdapter implements FindReservationPort {
	private final ReservationJpaRepository reservationJpaRepository;

	private final ReservationJpaMapper reservationJpaMapper;

	@Override
	public Reservation loadReservation(UUID reservationId) {
		final ReservationJpaEntity found = this.reservationJpaRepository.findById(reservationId)
				.orElseThrow(() -> new EntityNotFoundException("Reservation not found: " + reservationId));
		return this.reservationJpaMapper.toDomain(found);
	}
}
