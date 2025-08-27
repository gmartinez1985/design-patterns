package com.germarna.patterns.decorator.hexagonalddd.integration.e2e;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationActionType;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationJpaEntity;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationRepository;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.ChangeDatesOfReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.integration.e2e.helpers.Dates;
import com.germarna.patterns.decorator.hexagonalddd.integration.e2e.helpers.JdbcAuditUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "app.startup-runner.enabled=false")
@DisplayName("IT – ChangeDatesOfReservationUseCase (Envers MOD + custom audit UPDATE)")
class ChangeDatesOfReservationUseCaseIT {

	@Autowired
	private ChangeDatesOfReservationUseCase changeDatesUseCase;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private JdbcTemplate jdbc;

	private JdbcAuditUtils audit;

	private static final UUID RES_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

	@BeforeEach
	void setUp() {
		this.audit = new JdbcAuditUtils(this.jdbc);
	}

	@Test
	@DisplayName("changeDates updates DB, creates Envers MOD revision, and logs custom UPDATE")
	@Sql(scripts = "/sql/change-dates/seed.sql")
	@Sql(scripts = "/sql/change-dates/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void changeDatesProducesAudits() {
		// GIVEN
		final Date newIn = Dates.noon(2025, Calendar.DECEMBER, 3);
		final Date newOut = Dates.noon(2025, Calendar.DECEMBER, 6);

		// WHEN
		final Reservation updated = this.updateDates(RES_ID, newIn, newOut);

		// THEN
		this.assertAggregateUpdated(updated, newIn, newOut);
		this.assertDatabaseUpdated(RES_ID, newIn, newOut);
		this.assertEnversAudit(RES_ID);
		this.assertCustomAuditUpdate(RES_ID);
	}

	// ----------------- HELPERS -----------------

	private Reservation updateDates(UUID id, Date checkIn, Date checkOut) {
		return this.changeDatesUseCase.changeDatesOfReservation(id, checkIn, checkOut);
	}

	private void assertAggregateUpdated(Reservation updated, Date expectedIn, Date expectedOut) {
		assertNotNull(updated, "Returned aggregate must not be null");
		assertAll(() -> assertEquals(expectedIn, updated.getCheckInDate()),
				() -> assertEquals(expectedOut, updated.getCheckOutDate()));
	}

	private void assertDatabaseUpdated(UUID id, Date expectedIn, Date expectedOut) {
		final ReservationJpaEntity persisted = this.reservationRepository.findById(id)
				.orElseThrow(() -> new AssertionError("Reservation not found after update"));
		assertAll(() -> assertEquals(expectedIn, persisted.getCheckInDate()),
				() -> assertEquals(expectedOut, persisted.getCheckOutDate()));
	}

	private void assertEnversAudit(UUID id) {
		assertTrue(this.audit.enversModCount(id) > 0, "Expected at least one Envers MOD revision");
		final long latestRev = this.audit.latestRevision(id);
		this.audit.assertHasMatchingRevinfo(latestRev);
	}

	private void assertCustomAuditUpdate(UUID id) {
		assertTrue(this.audit.customAuditCount(id, ReservationActionType.UPDATE.name()) > 0,
				"Expected custom audit UPDATE row");
	}
}
