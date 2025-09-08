package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.findreservation;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.findreservation.FindReservationPersistenceAdapter;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.common.mapper.ReservationJpaMapperImpl;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.helpers.JpaSliceTestSupport;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "app.startup-runner.enabled=false")
@Import({FindReservationPersistenceAdapter.class, ReservationJpaMapperImpl.class})
@ContextConfiguration(classes = {JpaSliceTestSupport.TestBoot.class, JpaSliceTestSupport.JpaOnlyConfig.class})
@DisplayName("JPA slice: FindReservationPersistenceAdapter")
class FindReservationPersistenceAdapterSliceTest {

	@Autowired
	FindReservationPersistenceAdapter adapter;

	@Test
	@Sql(scripts = "/sql/find_reservation/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/sql/find_reservation/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	@DisplayName("Loads an existing Reservation and maps it to domain")
	void loadsExistingReservation() {
		// GIVEN
		final UUID reservationId = UUID.fromString("ef5fedf8-9508-4211-a493-cbf55c1c31a4");

		// WHEN
		final Reservation reservationFound = this.adapter.loadReservation(reservationId);

		// THEN
		assertEquals(reservationId, reservationFound.getReservationId());
		assertEquals(UUID.fromString("7d33d088-2c64-464b-92e5-ff23b243990a"), reservationFound.getRoomId());
		assertEquals("Alan Turing", reservationFound.getGuestName());
		assertEquals("alan@example.com", reservationFound.getGuestEmail());
		assertNotNull(reservationFound.getCheckInDate());
		assertNotNull(reservationFound.getCheckOutDate());
		assertTrue(reservationFound.getCheckInDate().before(reservationFound.getCheckOutDate()));
	}

	@Test
	@DisplayName("Returns null (or throws) when Reservation does not exist")
	void returnsNullOrThrowsWhenMissing() {
		// GIVEN
		final UUID reservationId = UUID.fromString("52966b55-9e9e-4bb0-ba1e-65bae7d3f9f8");
		// WHEN - THEN
		assertThrows(EntityNotFoundException.class, () -> this.adapter.loadReservation(reservationId));
	}
}
