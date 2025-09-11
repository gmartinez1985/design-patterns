package com.germarna.patterns.observer.hexagonalddd.integration.e2e;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.model.ReservationMongoDocument;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.writemodel.repository.ReservationJpaRepository;
import com.germarna.patterns.observer.hexagonalddd.application.domain.response.ReservationView;
import com.germarna.patterns.observer.hexagonalddd.application.port.in.usecase.FindReservationUseCase;
import com.germarna.patterns.observer.hexagonalddd.testutils.ReservationMongoDocumentMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class FindReservationIT {

	@Autowired
	FindReservationUseCase findReservationUseCase;

	@Autowired
	MongoTemplate mongoTemplate;

	ReservationMongoDocument doc;

	@BeforeEach
	void seedMongo() {
		mongoTemplate.dropCollection(ReservationMongoDocument.class);
		doc = ReservationMongoDocumentMother.createMongoDocument();
		mongoTemplate.insert(doc);
	}

	@AfterEach
	void cleanMongo() {
		mongoTemplate.dropCollection(ReservationMongoDocument.class);
	}

	@Test
	@DisplayName("Given a room exists, when CreateReservationUseCase is executed, then reservation is stored in JPA and projected to Mongo")
	void givenReservationId_whenFindReservationUseCase_thenReservationIsFound() {
		// GIVEN
		UUID reservationId = doc.getReservationId();

		// WHEN
		ReservationView reservation = findReservationUseCase.findReservation(reservationId);

		// THEN
		assertEquals(reservationId, reservation.reservationId());
		assertEquals(doc.getGuestName(), reservation.guestName());
		assertEquals(doc.getGuestEmail(), reservation.guestEmail());
		assertEquals(doc.getCheckInDate(), reservation.checkInDate());
		assertEquals(doc.getCheckOutDate(), reservation.checkOutDate());
		assertEquals(doc.getRoom().getId(), reservation.room().id());
		assertEquals(doc.getRoom().getType(), reservation.room().type());
		assertEquals(doc.getRoom().getNumber(), reservation.room().number());
	}
}
