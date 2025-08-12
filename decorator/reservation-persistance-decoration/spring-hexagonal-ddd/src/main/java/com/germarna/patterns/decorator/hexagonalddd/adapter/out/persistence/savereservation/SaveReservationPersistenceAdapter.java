package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.savereservation;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationJpaEntity;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.mapper.ReservationMapper;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationRepository;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveReservationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SaveReservationPersistenceAdapter implements SaveReservationPort {
	private final ReservationRepository reservationRepository;
	private final ReservationMapper reservationMapper;

	@Override
	public UUID saveReservation(Reservation reservation) {
		final ReservationJpaEntity entity = this.reservationMapper.toEntity(reservation);
		final ReservationJpaEntity saved = this.reservationRepository.save(entity);
		return saved.getReservationId();
	}
}
