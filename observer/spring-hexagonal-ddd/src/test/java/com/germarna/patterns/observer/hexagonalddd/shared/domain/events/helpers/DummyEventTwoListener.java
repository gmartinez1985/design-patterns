package com.germarna.patterns.observer.hexagonalddd.shared.domain.events.helpers;

import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.DomainEvent;
import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.Observer;

public class DummyEventTwoListener implements Observer<DummyEventTwo> {
	public int updateCount = 0;
	public DomainEvent receivedEvent = null;

	@Override
	public void update(DummyEventTwo event) {
		this.updateCount++;
		this.receivedEvent = event;
	}
}
