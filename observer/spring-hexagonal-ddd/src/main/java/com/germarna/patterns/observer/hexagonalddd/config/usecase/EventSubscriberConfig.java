package com.germarna.patterns.observer.hexagonalddd.config.usecase;

import com.germarna.patterns.observer.hexagonalddd.adapter.in.event.ReservationCreatedEmailObserver;
import com.germarna.patterns.observer.hexagonalddd.adapter.in.event.ReservationCreatedSlackObserver;
import com.germarna.patterns.observer.hexagonalddd.adapter.in.event.ReservationCreatedSmsObserver;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.Subject;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventSubscriberConfig {

	private final ReservationCreatedEmailObserver reservationCreatedEmailObserver;

	private final ReservationCreatedSlackObserver reservationCreatedSlackObserver;

	private final ReservationCreatedSmsObserver reservationCreatedSmsObserver;

	@PostConstruct
	public void registerSubscribers() {
		Subject.INSTANCE.attach(this.reservationCreatedEmailObserver);
		Subject.INSTANCE.attach(this.reservationCreatedSlackObserver);
		Subject.INSTANCE.attach(this.reservationCreatedSmsObserver);
	}
}
