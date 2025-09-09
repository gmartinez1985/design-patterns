package com.germarna.patterns.observer.hexagonalddd.services.cqrs;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.savereservation.ReservationPersistedEvent;
import com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.model.ReservationSnapshot;
import com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.model.RoomSnapshot;
import com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.port.out.ReservationWriteModelLoader;
import com.germarna.patterns.observer.hexagonalddd.services.cqrs.writemodel.port.out.RoomWriteModelLoader;
import com.germarna.patterns.observer.hexagonalddd.services.cqrs.readmodel.port.in.ReservationReadModelUpserter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationProjectorService {
	private final ReservationWriteModelLoader reservationLoader;
	private final RoomWriteModelLoader roomLoader;
	private final ReservationProjectionMapper projectionMapper;
	private final ReservationReadModelUpserter readModelUpserter;

	@Transactional(readOnly = true)
	public void projectReservation(ReservationPersistedEvent event) {
		final ReservationSnapshot reservation = this.reservationLoader.load(event.reservationId());
		final RoomSnapshot room = this.roomLoader.load(reservation.roomId());
		final ReservationProjectionView view = this.projectionMapper.toView(reservation, room, event);
		this.readModelUpserter.upsert(view);
	}
}
