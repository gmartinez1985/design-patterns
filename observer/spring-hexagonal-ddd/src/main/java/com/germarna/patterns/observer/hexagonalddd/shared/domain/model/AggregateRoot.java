package com.germarna.patterns.observer.hexagonalddd.shared.domain.model;

import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.DomainEvent;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.Subject;

public abstract class AggregateRoot {

	protected void publish(DomainEvent event) {
		Subject.INSTANCE.notifyObservers(event);
	}
}
