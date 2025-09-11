package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.operations.findreservation;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.mapper.ReservationMongoMapperImpl;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.model.ReservationMongoDocument;
import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.repository.ReservationMongoRepository;
import com.germarna.patterns.observer.hexagonalddd.application.domain.response.ReservationView;
import com.germarna.patterns.observer.hexagonalddd.testutils.DateUtils;
import com.germarna.patterns.observer.hexagonalddd.testutils.ReservationMongoDocumentMother;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest(properties = "app.startup-runner.enabled=false")
@Import({FindReservationPersistenceAdapter.class, ReservationMongoMapperImpl.class})
@ActiveProfiles("test")
@DisplayName("Mongo slice: FindReservationPersistenceAdapter")
class FindReservationPersistenceAdapterSliceTest {

	@Autowired
	private ReservationMongoRepository reservationMongoRepository;

	@Autowired
	private FindReservationPersistenceAdapter adapter;

	ReservationMongoDocument doc;

	@Test
	@DisplayName("Returns ReservationView when it exists in the read model (Mongo)")
	void returnsReservationWhenExists() {
		// GIVEN
		doc = ReservationMongoDocumentMother.createMongoDocument();
		UUID reservationId = doc.getReservationId();

		reservationMongoRepository.save(doc);

		// WHEN
		ReservationView reservation = adapter.loadReservation(reservationId);

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

	@Test
	@DisplayName("Throws EntityNotFoundException when the Reservation does not exist")
	void throwsWhenMissing() {
		final UUID missingId = ReservationMongoDocumentMother.nonExistingReservationId();
		assertThrows(EntityNotFoundException.class, () -> adapter.loadReservation(missingId));
	}
}
