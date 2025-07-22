package com.germarna.patterns.observer.hexagonalddd.domain.shared.events;
public interface Observer {
	void update(DomainEvent event);
	boolean isSubscribedTo(Class<?> eventType);
}
