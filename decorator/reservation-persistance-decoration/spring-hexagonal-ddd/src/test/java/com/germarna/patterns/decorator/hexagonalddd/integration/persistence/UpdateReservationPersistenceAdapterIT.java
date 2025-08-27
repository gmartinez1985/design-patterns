package com.germarna.patterns.decorator.hexagonalddd.integration.persistence;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationJpaEntity;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.mapper.ReservationMapperImpl;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationRepository;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.updatereservation.UpdateReservationPersistenceAdapter;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = "app.startup-runner.enabled=false")
@Import({ReservationMapperImpl.class, UpdateReservationPersistenceAdapter.class})
class UpdateReservationPersistenceAdapterIT {

	@Autowired
	private UpdateReservationPersistenceAdapter updateAdapter;

	@Autowired
	private ReservationRepository reservationRepository;

	private static final UUID RES_ID = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
	private static final UUID ROOM_ID = UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
	private static final UUID GUEST_ID = UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");

	@Test
	@DisplayName("updateReservation updates existing JPA entity and returns updated aggregate (happy path)")
	@Sql("/sql/update-reservation/seed.sql")
	@Sql(value = "/sql/update-reservation/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void updateReservationUpdatesAndReturnsAggregate() {
		// GIVEN: nuevas fechas
		final Calendar cal = Calendar.getInstance();
		cal.set(2025, Calendar.OCTOBER, 2, 12, 0, 0);
		final Date newCheckIn = cal.getTime();
		cal.set(2025, Calendar.OCTOBER, 4, 12, 0, 0);
		final Date newCheckOut = cal.getTime();

		final Reservation toUpdate = new Reservation(RES_ID, ROOM_ID, GUEST_ID, newCheckIn, newCheckOut);

		// WHEN: actualizamos vía el adapter (SUT)
		final Reservation updated = this.updateAdapter.updateReservation(toUpdate);

		// THEN: el agregado devuelto refleja los cambios
		assertNotNull(updated);
		assertAll(() -> assertEquals(RES_ID, updated.getReservationId()),
				() -> assertEquals(ROOM_ID, updated.getRoomId()), () -> assertEquals(GUEST_ID, updated.getGuestId()),
				() -> assertEquals(newCheckIn, updated.getCheckInDate()),
				() -> assertEquals(newCheckOut, updated.getCheckOutDate()),
				() -> assertTrue(updated.getCheckInDate().before(updated.getCheckOutDate())));

		// AND: la entidad en BD también
		final ReservationJpaEntity persisted = this.reservationRepository.findById(RES_ID)
				.orElseThrow(() -> new AssertionError("Reservation not found after update"));
		assertAll(() -> assertEquals(RES_ID, persisted.getReservationId()),
				() -> assertEquals(ROOM_ID, persisted.getRoomId()),
				() -> assertEquals(GUEST_ID, persisted.getGuestId()),
				() -> assertEquals(newCheckIn, persisted.getCheckInDate()),
				() -> assertEquals(newCheckOut, persisted.getCheckOutDate()));
	}
}
