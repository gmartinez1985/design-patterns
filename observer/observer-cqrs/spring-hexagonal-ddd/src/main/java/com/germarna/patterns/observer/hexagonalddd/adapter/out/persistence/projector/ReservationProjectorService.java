package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.model.ReservationMongoDocument;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.repository.ReservationMongoRepository;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.ReservationJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.RoomJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.ReservationJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.RoomJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.operations.savereservation.ReservationPersistedEvent;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationProjectorService {
	private final ReservationJpaRepository reservationJpaRepository;
	private final RoomJpaRepository roomJpaRepository;
	private final ReservationProjectionMapper projectionMapper;
	private final ReservationMongoRepository reservationMongoRepository;

	@Transactional(readOnly = true)
	public void projectReservation(ReservationPersistedEvent event) {
		final UUID reservationId = event.reservationId();
		final UUID roomId = event.roomId();

		final ReservationJpaEntity reservation =  this.reservationJpaRepository.findById(reservationId)
				.orElseThrow(() -> new EntityNotFoundException("Reservation not found: " + reservationId));
		final RoomJpaEntity room = this.roomJpaRepository.findById(roomId)
				.orElseThrow(() -> new EntityNotFoundException("Room not found: " + roomId));
		ReservationMongoDocument document = projectionMapper.toMongoDocument(reservation, room, event);
		reservationMongoRepository.save(document);
	}
}
