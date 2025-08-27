package com.germarna.patterns.decorator.hexagonalddd.integration.persistence;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.entity.ReservationJpaEntity;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.mapper.ReservationMapperImpl;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.common.repository.ReservationRepository;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence.savereservation.SaveReservationPersistenceAdapter;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.model.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(properties = "app.startup-runner.enabled=false")
@Import({ReservationMapperImpl.class, SaveReservationPersistenceAdapter.class})
class SaveReservationPersistenceAdapterIT {

	@Autowired
	private SaveReservationPersistenceAdapter saveAdapter;

	@Autowired
	private ReservationRepository reservationRepository;

	@Test
	@DisplayName("saveReservation persists JPA entity and returns its id (happy path)")
	void saveReservationPersistsAndReturnsId() {
		// GIVEN
		final UUID id = UUID.randomUUID();
		final UUID room = UUID.randomUUID();
		final UUID guest = UUID.randomUUID();

		final Calendar cal = Calendar.getInstance();
		cal.set(2025, Calendar.OCTOBER, 1, 12, 0, 0);
		final Date checkIn = cal.getTime();
		cal.set(2025, Calendar.OCTOBER, 3, 12, 0, 0);
		final Date checkOut = cal.getTime();

		final Reservation toSave = new Reservation(id, room, guest, checkIn, checkOut);

		// WHEN
		final UUID returned = this.saveAdapter.saveReservation(toSave);

		// THEN
		assertEquals(id, returned, "Adapter must return the persisted aggregate id");

		final ReservationJpaEntity persisted = this.reservationRepository.findById(id)
				.orElseThrow(() -> new AssertionError("Reservation not found after save"));
		assertEquals(id, persisted.getReservationId());
		assertEquals(room, persisted.getRoomId());
		assertEquals(guest, persisted.getGuestId());
		assertEquals(checkIn, persisted.getCheckInDate());
		assertEquals(checkOut, persisted.getCheckOutDate());
	}
}
