package com.germarna.patterns.observer.hexagonalddd.domain.shared.events;

import java.util.ArrayList;
import java.util.List;

public class Subject {
	private static Subject instance;
    private final List<Observer> subscribers = new ArrayList<>();

	private Subject() {
	}

	public static Subject instance() {
		if (instance == null) {
			instance = new Subject();
		}
		return instance;
	}

	public void attach(Observer subscriber) {
		this.subscribers.add(subscriber);
	}

	public void notifyObservers(DomainEvent event) {
		for (final Observer subscriber : this.subscribers) {
			if (subscriber.isSubscribedTo(event.getClass())) {
				subscriber.update(event);
			}
		}
	}
}
