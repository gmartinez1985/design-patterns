package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.findreservation;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationJpaEntity;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.mapper.ReservationMapper;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationRepository;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FindReservationPersistenceAdapter implements FindReservationPort {
	private final ReservationRepository reservationRepository;
	private final ReservationMapper reservationMapper;

	@Override
	public Reservation loadReservation(UUID reservationId) {
		final ReservationJpaEntity found = this.reservationRepository.findById(reservationId)
				.orElseThrow(() -> new EntityNotFoundException("Reservation not found: " + reservationId));
		return this.reservationMapper.toDomain(found);
	}
}
