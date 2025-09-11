package com.germarna.patterns.observer.hexagonalddd.application.domain.service;

import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation.SaveReservationPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateReservationService unit tests")
class CreateReservationServiceTest {

	@Mock
	private SaveReservationPort port;

	@InjectMocks
	private CreateReservationService service;

	@Captor
	private ArgumentCaptor<Reservation> reservationCaptor;

	@Test
	@DisplayName("createReservation builds the aggregate and delegates to the port, returning the port's UUID")
	void createsReservationAndDelegatesToPort() {
		// GIVEN
		final UUID roomId = UUID.randomUUID();
		final String guestName = "Ada Lovelace";
		final String guestEmail = "ada@example.com";
		final Date checkIn = new Date(System.currentTimeMillis() + 86_400_000L); // +1 day
		final Date checkOut = new Date(System.currentTimeMillis() + 2 * 86_400_000L); // +2 days

		final UUID persistedId = UUID.randomUUID();
		when(this.port.saveReservation(any(Reservation.class))).thenReturn(persistedId);

		// WHEN
		final UUID result = this.service.createReservation(roomId, guestName, guestEmail, checkIn, checkOut);

		// THEN
		assertEquals(persistedId, result, "Should return the UUID from the port");

		verify(this.port, times(1)).saveReservation(this.reservationCaptor.capture());
		final Reservation captured = this.reservationCaptor.getValue();

		assertNotNull(captured, "Should build a Reservation aggregate");
		assertNotNull(captured.getReservationId(), "Reservation id must be generated");
		assertEquals(roomId, captured.getRoomId());
		assertEquals(guestName, captured.getGuestName());
		assertEquals(guestEmail, captured.getGuestEmail());
		assertEquals(checkIn, captured.getCheckInDate());
		assertEquals(checkOut, captured.getCheckOutDate());
	}

	@Test
	@DisplayName("Two calls generate different reservation ids")
	void generatesDifferentIdsAcrossCalls() {
		// GIVEN
		final UUID roomId = UUID.randomUUID();
		final String name = "Grace Hopper";
		final String email = "grace@example.com";
		final Date in = new Date(System.currentTimeMillis() + 86_400_000L);
		final Date out = new Date(System.currentTimeMillis() + 2 * 86_400_000L);

		when(this.port.saveReservation(any(Reservation.class))).thenReturn(UUID.randomUUID());

		// WHEN
		this.service.createReservation(roomId, name, email, in, out);
		this.service.createReservation(roomId, name, email, in, out);

		// THEN
		verify(this.port, times(2)).saveReservation(this.reservationCaptor.capture());
		final var values = this.reservationCaptor.getAllValues();

		final Reservation r1 = values.get(0);
		final Reservation r2 = values.get(1);

		assertNotNull(r1.getReservationId());
		assertNotNull(r2.getReservationId());
		assertNotEquals(r1.getReservationId(), r2.getReservationId(), "Each call should generate a different id");
	}
}
