package com.germarna.patterns.observer.hexagonalddd.application.domain.model;

import com.germarna.patterns.observer.hexagonalddd.shared.domain.model.AggregateRoot;

import java.util.UUID;

public class Room implements AggregateRoot {
	private UUID id;
	private final String number;
	private final RoomType type;

	public Room(String number, RoomType type) {
		this.id = UUID.randomUUID();
		this.number = number;
		this.type = type;
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNumber() {
		return this.number;
	}

	public RoomType getType() {
		return this.type;
	}
}
