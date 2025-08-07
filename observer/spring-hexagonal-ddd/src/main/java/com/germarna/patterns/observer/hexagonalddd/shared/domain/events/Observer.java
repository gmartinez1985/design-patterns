package com.germarna.patterns.observer.hexagonalddd.shared.domain.events;
public interface Observer {
	void update(DomainEvent event);
	boolean isSubscribedTo(Class<?> eventType);
}
