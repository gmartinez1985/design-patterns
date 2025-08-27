package com.germarna.patterns.decorator.hexagonalddd.integration.persistence;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.mapper.ReservationMapperImpl;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.findreservation.FindReservationPersistenceAdapter;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "app.startup-runner.enabled=false")
@Import({ReservationMapperImpl.class, FindReservationPersistenceAdapter.class})
class FindReservationPersistenceAdapterIT {

	@Autowired
	private FindReservationPersistenceAdapter findAdapter;

	private static final UUID RES_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
	private static final UUID ROOM_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
	private static final UUID GUEST_ID = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");

	@Test
	@DisplayName("loadReservation returns the stored aggregate (happy path)")
	@Sql("/sql/find-reservation/seed.sql")
	@Sql(value = "/sql/find-reservation/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void loadReservationReturnsStoredAggregate() {
		// WHEN
		final Reservation loaded = this.findAdapter.loadReservation(RES_ID);

		// THEN
		assertEquals(RES_ID, loaded.getReservationId());
		assertEquals(ROOM_ID, loaded.getRoomId());
		assertEquals(GUEST_ID, loaded.getGuestId());
		assertNotNull(loaded.getCheckInDate());
		assertNotNull(loaded.getCheckOutDate());
		assertTrue(loaded.getCheckInDate().before(loaded.getCheckOutDate()));
	}

	@Test
	@DisplayName("loadReservation throws EntityNotFoundException when ID does not exist")
	void loadReservationThrowsWhenNotFound() {
		assertThrows(EntityNotFoundException.class,
				() -> this.findAdapter.loadReservation(UUID.fromString("dddddddd-dddd-dddd-dddd-dddddddddddd")));
	}
}
