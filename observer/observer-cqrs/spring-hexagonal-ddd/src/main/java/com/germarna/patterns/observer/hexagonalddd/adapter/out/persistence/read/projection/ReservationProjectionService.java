package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.read.projection;

import com.germarna.patterns.observer.hexagonalddd.adapter.events.ReservationPersistedEvent;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.read.document.ReservationDocument;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.read.mapper.ReservationMongoMapper;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.common.entity.ReservationJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.common.entity.RoomJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.common.repository.ReservationJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.common.repository.RoomJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationProjectionService {
	private final ReservationJpaRepository reservationJpaRepository;
	private final RoomJpaRepository roomJpaRepository;
	// private final ReservationMongoRepository reservationMongoRepository;
	private final ReservationMongoMapper reservationMongoMapper;
	private final ReservationUpserter reservationUpserter;

	@Transactional(readOnly = true)
	public void projectReservation(ReservationPersistedEvent event) {
		final ReservationJpaEntity reservationJpaEntity = this.reservationJpaRepository.findById(event.reservationId())
				.orElseThrow(
						() -> new IllegalArgumentException("Reservation not found with id: " + event.reservationId()));
		final RoomJpaEntity roomJpaEntity = this.roomJpaRepository.findById(reservationJpaEntity.getRoomId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Room not found with id: " + reservationJpaEntity.getRoomId()));

		final ReservationDocument document = this.reservationMongoMapper.toDocument(reservationJpaEntity, roomJpaEntity,
				event);
		this.reservationUpserter.upsert(document);

		// try {
		// this.reservationMongoRepository.insert(document);
		// } catch (final DuplicateKeyException exception) {
		// System.out.println("Reservation with id " + event.reservationId() + " already
		// exists in MongoDB.");
		// }

	}
}
