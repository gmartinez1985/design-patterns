package com.germarna.patterns.decorator.hexagonalddd.application.domain.service;

import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveDummyPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateReservationServiceTest {
	@Mock
	private SaveDummyPort saveDummyPort;

	@InjectMocks
	private CreateReservationService service;

	@Test
	@DisplayName("CreateReservationService returns a non-null reservation ID on first call")
	void testCreateReservationFirstCall() {
		final UUID reservationId = this.service.createReservation(UUID.randomUUID(), UUID.randomUUID(), new Date(),
				new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000) // +1 day
		);

		assertNotNull(reservationId);
		verify(this.saveDummyPort, times(1)).save("First insert");
		verify(this.saveDummyPort, times(1)).save("Second insert");
	}

	@Test
	@DisplayName("CreateReservationService throws on second call")
	void testCreateReservationSecondCall() {
		// first call flips the flag
        this.service.createReservation(UUID.randomUUID(), UUID.randomUUID(), new Date(), new Date());

		final RuntimeException ex = assertThrows(RuntimeException.class,
				() -> this.service.createReservation(UUID.randomUUID(), UUID.randomUUID(), new Date(), new Date()));

		assertEquals("💥 Simulated failure on second call", ex.getMessage());
		verify(this.saveDummyPort, times(1)).save("Third insert");
	}

	// @Test
	// @DisplayName("CreateReservationService returns a non-null reservation ID")
	// void testCreateReservation() {
	// final CreateReservationUseCase service = new CreateReservationService();
	// final String guestName = "John Doe";
	// final String roomType = "Deluxe";
	//
	// final UUID reservationId = service.createReservation(guestName, roomType);
	//
	// assertNotNull(reservationId);
	// }
}
