package com.germarna.patterns.observer.hexagonalddd.shared.domain.events;
public interface Observer<E extends DomainEvent> {
	void update(E event);
}
