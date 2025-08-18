package com.germarna.patterns.observer.hexagonalddd.shared.domain.events;

import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.helpers.DummyEventOne;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.helpers.DummyEventOneListener;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.helpers.DummyEventTwo;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.helpers.DummyEventTwoListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {

	private DummyEventOneListener observerToDummyEventOne;
	private DummyEventTwoListener observerToDummyEventTwo;
	private DummyEventOne dummyEventOne;

	@BeforeEach
	void setUp() {
		// Subject is a singleton, so we must clear observers between tests to avoid
		// interference from previous test runs
		Subject.INSTANCE.removeObservers();
		this.observerToDummyEventOne = new DummyEventOneListener();
		this.observerToDummyEventTwo = new DummyEventTwoListener();
		final UUID reservationId = UUID.randomUUID();
		this.dummyEventOne = new DummyEventOne(reservationId);
	}

	@Test
	@DisplayName("Should notify only observers subscribed to the specific event type")
	void shouldNotifyOnlySubscribedObservers() {
		Subject.INSTANCE.attach(DummyEventOne.class, this.observerToDummyEventOne);
		Subject.INSTANCE.attach(DummyEventTwo.class, this.observerToDummyEventTwo);

		Subject.INSTANCE.notifyObservers(this.dummyEventOne);

		assertEquals(1, this.observerToDummyEventOne.updateCount, "Observer one should be updated once");
		assertEquals(this.dummyEventOne, this.observerToDummyEventOne.receivedEvent,
				"Observer one should receive the correct event");

		assertEquals(0, this.observerToDummyEventTwo.updateCount, "Observer two should not be updated");
		assertNull(this.observerToDummyEventTwo.receivedEvent, "Observer two should not receive any event");
	}

	@Test
	@DisplayName("Should notify all observers subscribed to the same event type")
	void shouldNotifyAllObserversSubscribedToSameEventType() {
		final DummyEventOneListener anotherObserverToDummyEventOne = new DummyEventOneListener();

		Subject.INSTANCE.attach(DummyEventOne.class, this.observerToDummyEventOne);
		Subject.INSTANCE.attach(DummyEventOne.class, anotherObserverToDummyEventOne);

		Subject.INSTANCE.notifyObservers(this.dummyEventOne);

		assertEquals(1, this.observerToDummyEventOne.updateCount);
		assertEquals(1, anotherObserverToDummyEventOne.updateCount);
		assertEquals(this.dummyEventOne, this.observerToDummyEventOne.receivedEvent);
		assertEquals(this.dummyEventOne, anotherObserverToDummyEventOne.receivedEvent);
	}

	@Test
	@DisplayName("Should not fail if no observers are subscribed to the event type")
	void shouldNotFailWhenNoObserversForEventType() {
		assertDoesNotThrow(() -> Subject.INSTANCE.notifyObservers(this.dummyEventOne));
	}

	@Test
	@DisplayName("Should not notify removed observers")
	void shouldNotNotifyRemovedObservers() {
		Subject.INSTANCE.attach(DummyEventOne.class, this.observerToDummyEventOne);
		Subject.INSTANCE.removeObserver(this.observerToDummyEventOne);

		Subject.INSTANCE.notifyObservers(this.dummyEventOne);

		assertEquals(0, this.observerToDummyEventOne.updateCount, "Removed observer should not be updated");
		assertNull(this.observerToDummyEventOne.receivedEvent, "Removed observer should not receive any event");
	}

	@Test
	@DisplayName("Should clear all observers when removeObservers is called")
	void shouldClearAllObservers() {
		Subject.INSTANCE.attach(DummyEventOne.class, this.observerToDummyEventOne);
		Subject.INSTANCE.attach(DummyEventTwo.class, this.observerToDummyEventTwo);

		Subject.INSTANCE.removeObservers();
		Subject.INSTANCE.notifyObservers(this.dummyEventOne);

		assertEquals(0, this.observerToDummyEventOne.updateCount, "Observer one should not be updated after clear");
		assertNull(this.observerToDummyEventOne.receivedEvent, "Observer one should not receive any event after clear");

		assertEquals(0, this.observerToDummyEventTwo.updateCount, "Observer two should not be updated after clear");
		assertNull(this.observerToDummyEventTwo.receivedEvent, "Observer two should not receive any event after clear");
	}

	@Test
	@DisplayName("Should notify observer multiple times if attached multiple times")
	void shouldNotifyObserverMultipleTimesIfAttachedMultipleTimes() {
		Subject.INSTANCE.attach(DummyEventOne.class, this.observerToDummyEventOne);
		Subject.INSTANCE.attach(DummyEventOne.class, this.observerToDummyEventOne);

		Subject.INSTANCE.notifyObservers(this.dummyEventOne);

		assertEquals(2, this.observerToDummyEventOne.updateCount,
				"Observer should be updated twice because it was attached twice");
		assertEquals(this.dummyEventOne, this.observerToDummyEventOne.receivedEvent);
	}

	@Test
	@DisplayName("Should notify observers for multiple sequential events correctly")
	void shouldNotifyObserversForMultipleEvents() {
		final DummyEventTwo dummyEventTwo = new DummyEventTwo(UUID.randomUUID());

		Subject.INSTANCE.attach(DummyEventOne.class, this.observerToDummyEventOne);
		Subject.INSTANCE.attach(DummyEventTwo.class, this.observerToDummyEventTwo);

		Subject.INSTANCE.notifyObservers(this.dummyEventOne);
		Subject.INSTANCE.notifyObservers(dummyEventTwo);

		assertEquals(1, this.observerToDummyEventOne.updateCount);
		assertEquals(1, this.observerToDummyEventTwo.updateCount);
		assertEquals(this.dummyEventOne, this.observerToDummyEventOne.receivedEvent);
		assertEquals(dummyEventTwo, this.observerToDummyEventTwo.receivedEvent);
	}

	@Test
	@DisplayName("Should remove only the specified observer")
	void shouldRemoveSpecificObserverOnly() {
		final DummyEventOneListener anotherObserver = new DummyEventOneListener();
		Subject.INSTANCE.attach(DummyEventOne.class, this.observerToDummyEventOne);
		Subject.INSTANCE.attach(DummyEventOne.class, anotherObserver);

		Subject.INSTANCE.removeObserver(this.observerToDummyEventOne);
		Subject.INSTANCE.notifyObservers(this.dummyEventOne);

		assertEquals(0, this.observerToDummyEventOne.updateCount);
		assertEquals(1, anotherObserver.updateCount);
	}

	@Test
	@DisplayName("Should handle notifying many observers")
	void shouldNotifyManyObservers() {
		for (int i = 0; i < 100; i++) {
			Subject.INSTANCE.attach(DummyEventOne.class, new DummyEventOneListener());
		}
		assertDoesNotThrow(() -> Subject.INSTANCE.notifyObservers(this.dummyEventOne));
	}

	@Test
	@DisplayName("Should handle null events gracefully")
	void shouldHandleNullEventsGracefully() {
		Subject.INSTANCE.attach(DummyEventOne.class, this.observerToDummyEventOne);
		assertDoesNotThrow(() -> Subject.INSTANCE.notifyObservers(null));
	}
}