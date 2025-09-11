package com.germarna.patterns.observer.hexagonalddd.application.domain.service;

import com.germarna.patterns.observer.hexagonalddd.application.domain.model.Reservation;
import com.germarna.patterns.observer.hexagonalddd.application.domain.response.ReservationView;
import com.germarna.patterns.observer.hexagonalddd.application.port.out.reservation.FindReservationPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FindReservationService unit tests")
class FindReservationServiceTest {

	@Mock
	private FindReservationPort findReservationPort;

	@InjectMocks
	private FindReservationService service;

	@Test
	@DisplayName("Delegates to FindReservationPort.loadReservation and returns the same aggregate")
	void delegatesToPortAndReturnsAggregate() {
		// GIVEN
		final UUID reservationId = UUID.randomUUID();
		final UUID roomId = UUID.randomUUID();
        final String roomType = "DOUBLE";
        final String roomNumber = "101";
		final String guestName = "Ada Lovelace";
		final String guestEmail = "ada@example.com";
		final Date checkIn = new Date(System.currentTimeMillis() + 86_400_000L);
		final Date checkOut = new Date(System.currentTimeMillis() + 2 * 86_400_000L);

		final ReservationView expected = new ReservationView(reservationId, guestName, guestEmail, checkIn, checkOut, new ReservationView.RoomView(roomId, roomType, roomNumber));

		when(this.findReservationPort.loadReservation(reservationId)).thenReturn(expected);

		// WHEN
		final ReservationView result = this.service.findReservation(reservationId);

		// THEN
		assertSame(expected, result, "Service should return the exact instance provided by the port");
		verify(this.findReservationPort, times(1)).loadReservation(reservationId);
		verifyNoMoreInteractions(this.findReservationPort);
	}

	@Test
	@DisplayName("Propagates exceptions thrown by the port")
	void propagatesExceptionsFromPort() {
		// GIVEN
		final UUID reservationId = UUID.randomUUID();
		when(this.findReservationPort.loadReservation(reservationId))
				.thenThrow(new IllegalArgumentException("not found"));

		// WHEN / THEN
		final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> this.service.findReservation(reservationId), "Service should propagate exceptions from the port");
		assertEquals("not found", ex.getMessage());
		verify(this.findReservationPort).loadReservation(reservationId);
		verifyNoMoreInteractions(this.findReservationPort);
	}
}
