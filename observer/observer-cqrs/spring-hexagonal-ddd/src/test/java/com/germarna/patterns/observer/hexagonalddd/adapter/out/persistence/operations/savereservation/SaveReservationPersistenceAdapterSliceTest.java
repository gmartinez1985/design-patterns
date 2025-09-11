package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.operations.savereservation;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.mapper.ReservationJpaMapperImpl;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.ReservationJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.ReservationJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.operations.helpers.JpaSliceTestSupport;
import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.transaction.TestTransaction;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "app.startup-runner.enabled=false")
@RecordApplicationEvents
@Import({SaveReservationPersistenceAdapter.class, ReservationJpaMapperImpl.class})
@ContextConfiguration(classes = {JpaSliceTestSupport.TestBoot.class, JpaSliceTestSupport.JpaOnlyConfig.class})

@DisplayName("JPA slice: SaveReservationPersistenceAdapter")
class SaveReservationPersistenceAdapterSliceTest {

	private static final UUID DUMMY_ROOM_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

	@Autowired
	SaveReservationPersistenceAdapter saveAdapter;
	@Autowired
	ReservationJpaRepository reservationJpaRepository;
	@Autowired
	ApplicationEvents applicationEvents;

	@Test
	@Sql(scripts = "/sql/save_reservation/insert_room.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/sql/save_reservation/cleanup.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	@DisplayName("Persists in JPA and publishes ReservationPersistedEvent")
	void persistsReservation_andPublishesEvent() {
		// GIVEN
		final Reservation reservation = new Reservation(UUID.randomUUID(), DUMMY_ROOM_ID, "Ada Lovelace",
				"ada@example.com", JpaSliceTestSupport.tomorrow(), JpaSliceTestSupport.dayAfterTomorrow());

		// WHEN
		final UUID savedReservationId = this.saveAdapter.saveReservation(reservation);
		this.forceCommitForTransactionalEvents();

		// THEN
		this.assertReservationIsPersisted(savedReservationId, reservation, DUMMY_ROOM_ID);
		this.assertEventIsPublished(reservation);
	}

	/*
	 * The listener for ReservationPersistedEvent waits until the transaction is
	 * committed. Since that listener runs outside the scope of this test, we must
	 * actually commit here so the event is fired after commit and we can verify it.
	 */
	private void forceCommitForTransactionalEvents() {
		assert TestTransaction.isActive();
		this.reservationJpaRepository.flush();
		TestTransaction.flagForCommit();
		TestTransaction.end();
	}

	private void assertReservationIsPersisted(UUID persistedId, Reservation expected, UUID expectedRoomId) {
		assertThat(persistedId).isNotNull();

		final Optional<ReservationJpaEntity> found = this.reservationJpaRepository.findById(persistedId);
		assertThat(found).isPresent();

		final var reservationJpa = found.get();
		assertThat(reservationJpa.getReservationId()).isEqualTo(expected.getReservationId());
		assertThat(reservationJpa.getGuestName()).isEqualTo(expected.getGuestName());
		assertThat(reservationJpa.getGuestEmail()).isEqualTo(expected.getGuestEmail());
		assertThat(reservationJpa.getRoomId()).isEqualTo(expectedRoomId);
		assertThat(reservationJpa.getCheckInDate().getTime()).isEqualTo(expected.getCheckInDate().getTime());
		assertThat(reservationJpa.getCheckOutDate().getTime()).isEqualTo(expected.getCheckOutDate().getTime());
	}

	private void assertEventIsPublished(Reservation reservation) {
		final long count = this.applicationEvents.stream(ReservationPersistedEvent.class).count();
		final var event = this.applicationEvents.stream(ReservationPersistedEvent.class).findFirst().orElseThrow();
		assertThat(count).isEqualTo(1);
		assertThat(event.reservationId()).isEqualTo(reservation.getReservationId());
		assertThat(event.roomId()).isEqualTo(reservation.getRoomId());
	}
}
