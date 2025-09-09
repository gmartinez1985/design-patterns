package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.operations.savereservation;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.ReservationJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.mapper.ReservationJpaMapper;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.ReservationJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation.SaveReservationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SaveReservationPersistenceAdapter implements SaveReservationPort {
	private final ReservationJpaRepository reservationJpaRepository;
	private final ReservationJpaMapper reservationJpaMapper;
	private final ApplicationEventPublisher eventPublisher;

	@Override
	public UUID saveReservation(Reservation reservation) {
		final ReservationJpaEntity entity = this.reservationJpaMapper.toEntity(reservation);
		final ReservationJpaEntity saved = this.reservationJpaRepository.save(entity);
		this.eventPublisher.publishEvent(
				new ReservationPersistedEvent(saved.getReservationId(), saved.getRoomId(), saved.getCheckInDate(),
						saved.getCheckOutDate(), saved.getGuestName(), saved.getGuestEmail(), Instant.now()));
		return saved.getReservationId();
	}
}
