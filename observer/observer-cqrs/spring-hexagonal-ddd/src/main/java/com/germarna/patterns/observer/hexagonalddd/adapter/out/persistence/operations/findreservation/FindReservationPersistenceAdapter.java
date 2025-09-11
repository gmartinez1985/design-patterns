package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.operations.findreservation;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.mapper.ReservationMongoMapper;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.model.ReservationMongoDocument;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.repository.ReservationMongoRepository;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.application.domain.response.ReservationView;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation.FindReservationPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FindReservationPersistenceAdapter implements FindReservationPort {
	private final ReservationMongoRepository reservationMongoRepository;
	private final ReservationMongoMapper reservationMongoMapper;

	@Override
	public ReservationView loadReservation(UUID reservationId) {
		final ReservationMongoDocument found = reservationMongoRepository.findById(reservationId).orElseThrow(() -> new EntityNotFoundException("Reservation not found: " + reservationId));
		return this.reservationMongoMapper.toDomain(found);
	}
}
