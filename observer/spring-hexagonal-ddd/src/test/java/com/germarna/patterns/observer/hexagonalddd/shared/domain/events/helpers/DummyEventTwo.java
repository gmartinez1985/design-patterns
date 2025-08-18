package com.germarna.patterns.observer.hexagonalddd.shared.domain.events.helpers;

import com.germarna.patterns.observer.hexagonalddd.shared.domain.events.DomainEvent;

import java.util.UUID;

public record DummyEventTwo(UUID dummyId) implements DomainEvent {

}
