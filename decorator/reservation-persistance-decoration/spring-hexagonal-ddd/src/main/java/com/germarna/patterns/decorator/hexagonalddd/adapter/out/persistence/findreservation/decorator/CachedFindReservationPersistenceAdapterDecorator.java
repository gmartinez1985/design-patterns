package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.findreservation.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.FindReservationPort;
import org.springframework.cache.annotation.Cacheable;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CachedFindReservationPersistenceAdapterDecorator extends BaseFindReservationPersistenceAdapterDecorator {

	private final AtomicInteger callCount = new AtomicInteger();
	public CachedFindReservationPersistenceAdapterDecorator(FindReservationPort delegate) {
		super(delegate);
	}

	@Override
	@Cacheable(value = "reservations", key = "#reservationId")
	public Reservation loadReservation(UUID reservationId) {
		final int currentCall = this.callCount.incrementAndGet();
		System.out.println("⬇️ Cache MISS for reservation " + reservationId + " (call #" + currentCall + ")");
		return super.loadReservation(reservationId);
	}
}
