package com.germarna.patterns.observer.hexagonalddd.adapter.in.event;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.savereservation.ReservationPersistedEvent;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.projector.ReservationProjectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ReservationPersistedEventListener {
	private final ReservationProjectorService reservationProjectorService;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void on(ReservationPersistedEvent reservationPersistedEvent) {
		this.reservationProjectorService.projectReservation(reservationPersistedEvent);
	}
}
