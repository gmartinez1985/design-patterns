package com.germarna.patterns.observer.hexagonalddd.adapter.in.event;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.savereservation.ReservationPersistedEvent;
import com.germarna.patterns.observer.hexagonalddd.services.readmodel.projection.ReservationProjectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ReservationPersistedEventListener {
	private final ReservationProjectionService reservationProjectionService;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void on(ReservationPersistedEvent reservationPersistedEvent) {
		this.reservationProjectionService.projectReservation(reservationPersistedEvent);
	}
}
