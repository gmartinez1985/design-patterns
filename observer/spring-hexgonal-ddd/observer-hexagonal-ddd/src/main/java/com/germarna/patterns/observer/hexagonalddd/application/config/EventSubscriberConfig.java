package com.germarna.patterns.observer.hexagonalddd.application.config;

import com.germarna.patterns.observer.hexagonalddd.application.notification.observer.ReservationCreatedEmailObserver;
import com.germarna.patterns.observer.hexagonalddd.application.notification.observer.ReservationCreatedSlackObserver;
import com.germarna.patterns.observer.hexagonalddd.application.notification.observer.ReservationCreatedSmsObserver;
import com.germarna.patterns.observer.hexagonalddd.domain.shared.events.Subject;
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
		Subject.getInstance().attach(this.reservationCreatedEmailObserver);
		Subject.getInstance().attach(this.reservationCreatedSlackObserver);
		Subject.getInstance().attach(this.reservationCreatedSmsObserver);
	}
}
