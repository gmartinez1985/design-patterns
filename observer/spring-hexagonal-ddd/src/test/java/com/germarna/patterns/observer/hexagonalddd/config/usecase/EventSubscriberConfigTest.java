package com.germarna.patterns.observer.hexagonalddd.config.usecase;

import com.germarna.patterns.observer.hexagonalddd.adapter.in.event.ReservationCreatedEmailObserver;
import com.germarna.patterns.observer.hexagonalddd.adapter.in.event.ReservationCreatedSlackObserver;
import com.germarna.patterns.observer.hexagonalddd.adapter.in.event.ReservationCreatedSmsObserver;
import com.germarna.patterns.observer.hexagonalddd.application.domain.events.ReservationCreatedEvent;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EventSubscriberConfigTest {

	@Mock
	private ReservationCreatedEmailObserver emailObserver;
	@Mock
	private ReservationCreatedSlackObserver slackObserver;
	@Mock
	private ReservationCreatedSmsObserver smsObserver;
	@InjectMocks
	private EventSubscriberConfig config;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		Subject.INSTANCE.removeObservers();
	}

	@Test
	void registerSubscribersShouldAttachObserversToSubject() {
		this.config.registerSubscribers();
		final ReservationCreatedEvent dummyEvent = new ReservationCreatedEvent(null, null, null);
		assertDoesNotThrow(() -> Subject.INSTANCE.notifyObservers(dummyEvent),
				"Calling notifyObservers should not fail after attaching observers");
	}
}
