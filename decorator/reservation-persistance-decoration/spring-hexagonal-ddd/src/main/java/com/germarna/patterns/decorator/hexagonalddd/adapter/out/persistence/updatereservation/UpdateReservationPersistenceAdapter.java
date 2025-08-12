package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.updatereservation;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationJpaEntity;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.mapper.ReservationMapper;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationRepository;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.UpdateReservationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("updateReservationPersistenceAdapter")
public class UpdateReservationPersistenceAdapter implements UpdateReservationPort {
	private final ReservationRepository reservationRepository;
	private final ReservationMapper reservationMapper;

	@Override
	public Reservation updateReservation(Reservation reservation) {
		final ReservationJpaEntity entity = this.reservationMapper.toEntity(reservation);
		final ReservationJpaEntity updated = this.reservationRepository.save(entity);
		return this.reservationMapper.toDomain(updated);
	}
}
