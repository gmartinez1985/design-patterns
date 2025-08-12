package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.updatereservation.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.UpdateReservationPort;
import org.springframework.cache.annotation.CachePut;

import java.util.concurrent.atomic.AtomicInteger;

public class CachedUpdateReservationPersistenceDecorator extends BaseUpdateReservationPersistenceDecorator {

	private final AtomicInteger callCount = new AtomicInteger();
	public CachedUpdateReservationPersistenceDecorator(UpdateReservationPort delegate) {
		super(delegate);
	}

	@Override
	@CachePut(value = "reservations", key = "#reservation.reservationId")
	public Reservation updateReservation(Reservation reservation) {
		final int currentCall = this.callCount.incrementAndGet();
		System.out.println(
				"♻️ Cache UPDATED for reservation " + reservation.getReservationId() + " (call #" + currentCall + ")");
		return super.updateReservation(reservation);
	}
}
