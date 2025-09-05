package com.germarna.patterns.observer.hexagonalddd.services.readmodel.projection;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.savereservation.ReservationPersistedEvent;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Room;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation.FindReservationPort;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.room.FindRoomPort;
import com.germarna.patterns.observer.hexagonalddd.services.readmodel.projection.mapper.ReservationProjectionMapper;
import com.germarna.patterns.observer.hexagonalddd.services.readmodel.projection.model.ReservationProjectionView;
import com.germarna.patterns.observer.hexagonalddd.services.readmodel.projection.port.out.ReservationReadModelUpserter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationProjectionService {
	private final ReservationProjectionMapper reservationProjectionMapper;
	private final ReservationReadModelUpserter reservationReadModelUpserter;
	private final FindReservationPort findReservationPort;
	private final FindRoomPort findRoomPort;

	@Transactional(readOnly = true)
	public void projectReservation(ReservationPersistedEvent event) {
		final Reservation reservation = this.findReservationPort.loadReservation(event.reservationId());
		final Room room = this.findRoomPort.loadRoom(reservation.getRoomId());
		final ReservationProjectionView view = this.reservationProjectionMapper.toView(reservation, room, event);
		this.reservationReadModelUpserter.upsert(view);
	}
}
