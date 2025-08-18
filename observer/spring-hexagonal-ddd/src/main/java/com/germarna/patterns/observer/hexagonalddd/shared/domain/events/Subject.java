package com.germarna.patterns.observer.hexagonalddd.shared.domain.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Subject {
	INSTANCE;
	private final Map<Class<? extends DomainEvent>, List<Observer<? extends DomainEvent>>> subscribers = new HashMap<>();

	public <T extends DomainEvent> void attach(Class<T> eventType, Observer<T> observer) {
		this.subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(observer);
	}

	@SuppressWarnings("unchecked")
	public <T extends DomainEvent> void notifyObservers(T event) {
		if (event == null) {
			return;
		}
		final List<Observer<? extends DomainEvent>> list = this.subscribers.getOrDefault(event.getClass(), List.of());
		for (final Observer<? extends DomainEvent> obs : list) {
			((Observer<T>) obs).update(event);
		}
	}

	public <T extends DomainEvent> void removeObserver(Observer<T> observer) {
		for (final List<Observer<? extends DomainEvent>> observers : this.subscribers.values()) {
			observers.remove(observer);
		}
	}
	public void removeObservers() {
		this.subscribers.clear();
	}
}
