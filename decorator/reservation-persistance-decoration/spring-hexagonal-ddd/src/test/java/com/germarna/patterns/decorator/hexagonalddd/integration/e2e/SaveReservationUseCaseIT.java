package com.germarna.patterns.decorator.hexagonalddd.integration.e2e;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationActionType;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationJpaEntity;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationRepository;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.SaveReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.integration.e2e.helpers.Dates;
import com.germarna.patterns.decorator.hexagonalddd.integration.e2e.helpers.JdbcAuditUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "app.startup-runner.enabled=false")
@DisplayName("IT – SaveReservationUseCase (persist + Envers + custom audit decorator)")
class SaveReservationUseCaseIT {

	@Autowired
	private SaveReservationUseCase saveReservationUseCase;
	@Autowired
	private ReservationRepository reservationRepository;
	@Autowired
	private JdbcTemplate jdbc;

	private JdbcAuditUtils audit;

	@BeforeEach
	void setUp() {
		this.audit = new JdbcAuditUtils(this.jdbc);
	}

	@Test
	@DisplayName("createReservation persists, writes Envers ADD revision and custom audit CREATE")
	void createReservationPersistsAndAudits() {
		// GIVEN
		final UUID room = UUID.randomUUID();
		final UUID guest = UUID.randomUUID();
		final var checkIn = Dates.noon(2025, Calendar.AUGUST, 27);
		final var checkOut = Dates.noon(2025, Calendar.AUGUST, 29);

		// WHEN
		final UUID id = this.createReservation(room, guest, checkIn, checkOut);

		// THEN
		this.assertRowPersisted(id, room, guest, checkIn, checkOut);
		this.assertEnversAudit(id);
		this.assertCustomAudit(id);
	}

	// ----------------- HELPERS -----------------

	private UUID createReservation(UUID room, UUID guest, Date checkIn, Date checkOut) {
		return this.saveReservationUseCase.createReservation(room, guest, checkIn, checkOut);
	}

	private void assertRowPersisted(UUID id, UUID room, UUID guest, Date checkIn, Date checkOut) {
		final ReservationJpaEntity persisted = this.reservationRepository.findById(id)
				.orElseThrow(() -> new AssertionError("Reservation not found in DB after save"));
		assertAll(() -> assertEquals(room, persisted.getRoomId()), () -> assertEquals(guest, persisted.getGuestId()),
				() -> assertEquals(checkIn, persisted.getCheckInDate()),
				() -> assertEquals(checkOut, persisted.getCheckOutDate()));
	}

	private void assertEnversAudit(UUID id) {
		assertTrue(this.audit.enversAddCount(id) > 0, "Expected Envers ADD revision");
		final long latestRev = this.audit.latestRevision(id);
		this.audit.assertHasMatchingRevinfo(latestRev);
	}

	private void assertCustomAudit(UUID id) {
		assertTrue(this.audit.customAuditCount(id, ReservationActionType.CREATE.name()) > 0,
				"Expected custom audit CREATE row");
	}
}
