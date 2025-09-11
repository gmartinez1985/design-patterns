package com.germarna.patterns.observer.hexagonalddd.application.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Room domain model")
class RoomTest {
	@Test
	@DisplayName("Constructor assigns number & type and generates a non-null id")
	void constructorAssignsFieldsAndGeneratesId() {
		// GIVEN
		final String number = "101";
		final RoomType type = RoomType.DOUBLE;

		// WHEN
		final Room room = new Room(number, type);

		// THEN
		assertNotNull(room, "Room should be created");
		assertEquals(number, room.getNumber(), "number must match the constructor argument");
		assertEquals(type, room.getType(), "type must match the constructor argument");
		assertNotNull(room.getId(), "id should be generated in the constructor");
	}

	@Test
	@DisplayName("setId overrides the generated id")
	void setIdOverridesGeneratedId() {
		// GIVEN
		final Room room = new Room("202", RoomType.SUITE);
		final UUID original = room.getId();

		// WHEN
		final UUID newId = UUID.randomUUID();
		room.setId(newId);

		// THEN
		final UUID current = room.getId();
		assertNotNull(original);
		assertEquals(newId, current, "setId should replace the current id value");
		assertNotEquals(original, current, "id should actually change after setId");
	}

	@Test
	@DisplayName("getNumber returns the assigned number")
	void getNumberReturnsValue() {
		// GIVEN
		final String number = "303";
		final Room room = new Room(number, RoomType.SINGLE);

		// WHEN
		final String returned = room.getNumber();

		// THEN
		assertEquals(number, returned);
	}

	@Test
	@DisplayName("getType returns the assigned type")
	void getTypeReturnsValue() {
		// GIVEN
		final String number = "404";
		final RoomType type = RoomType.SINGLE;
		final Room room = new Room(number, type);

		// WHEN
		final RoomType returned = room.getType();

		// THEN
		assertEquals(type, returned);
	}
}
