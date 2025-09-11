package com.germarna.patterns.observer.hexagonalddd.integration.e2e;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.model.ReservationMongoDocument;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.repository.ReservationMongoRepository;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.model.ReservationJpaEntity;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.ReservationJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.RoomJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase.CreateReservationUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static com.germarna.patterns.observer.hexagonalddd.testutils.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CreateReservationIT {

	@Autowired
	CreateReservationUseCase createReservationUseCase;

	@Autowired
	RoomJpaRepository roomJpaRepository;

	@Autowired
	ReservationJpaRepository reservationJpaRepository;

	@Autowired
	ReservationMongoRepository reservationMongoRepository;

	@Test
	@DisplayName("Given a room exists, when CreateReservationUseCase is executed, then reservation is stored in JPA and projected to Mongo")
	@Sql(scripts = "/sql/integration/e2e/save_reservation/insert_room.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/sql/integration/e2e/save_reservation/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void givenRoom_whenCreateReservation_thenJpaAndMongoHaveData() {
		// GIVEN
		final UUID ROOM_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

		// WHEN
		UUID reservationId = createReservationUseCase.createReservation(
				ROOM_ID,
				"Ada Lovelace",
				"ada@example.com",
				tomorrow(),
				dayAfterTomorrow()
		);

		// THEN
		var jpaOpt = reservationJpaRepository.findById(reservationId);
		assertTrue(jpaOpt.isPresent(), "Reservation should exist in JPA");

		var mongoOpt = reservationMongoRepository.findById(reservationId);
		assertTrue(mongoOpt.isPresent(), "Reservation should be projected to Mongo");

		assertJpaAndMongoAreEquals(jpaOpt.get(), mongoOpt.get());
	}

	private void assertJpaAndMongoAreEquals(ReservationJpaEntity jpa, ReservationMongoDocument mongo) {
		assertEquals(jpa.getReservationId(), mongo.getReservationId(), "reservationId");
		assertEquals(jpa.getRoomId(),        mongo.getRoom().getId(),        "roomId");
		assertEquals("101",        mongo.getRoom().getNumber(),        "roomNumber");
		assertEquals("DOUBLE",        mongo.getRoom().getType(),        "roomType");
		assertEquals(jpa.getGuestName(),     mongo.getGuestName(),     "guestName");
		assertEquals(jpa.getGuestEmail(),    mongo.getGuestEmail(),    "guestEmail");
		assertEquals(
				toUtcLocalDate(jpa.getCheckInDate()),
				toUtcLocalDate(mongo.getCheckInDate()),
				"checkInDate"
		);
		assertEquals(
				toUtcLocalDate(jpa.getCheckOutDate()),
				toUtcLocalDate(mongo.getCheckOutDate()),
				"checkOutDate"
		);
	}
}
