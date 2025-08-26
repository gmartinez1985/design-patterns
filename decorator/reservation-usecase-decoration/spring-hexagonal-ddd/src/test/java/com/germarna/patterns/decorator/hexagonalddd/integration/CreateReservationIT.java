package com.germarna.patterns.decorator.hexagonalddd.integration;

import com.germarna.patterns.decorator.hexagonalddd.application.port.in.CreateReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveDummyPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = "app.startup-runner.enabled=false")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CreateReservationIT {

	@Autowired
	private CreateReservationUseCase createReservationUseCase;

	@MockitoSpyBean
	private SaveDummyPort saveDummyPort;

	@Test
	@DisplayName("When creating a reservation for the first time, then it should succeed and persist twice")
	void shouldCreateReservationSuccessfully() {
		// --- GIVEN ---
		final UUID roomId = UUID.randomUUID();
		final UUID guestId = UUID.randomUUID();
		final Date checkIn = new Date(); // today
		final Date checkOut = new Date(checkIn.getTime() + 2 * 24 * 60 * 60 * 1000); // +2 days

		// --- WHEN ---
		final UUID reservationId = this.createReservationUseCase.createReservation(roomId, guestId, checkIn, checkOut);

		// --- THEN ---
		assertNotNull(reservationId, "Reservation ID should not be null");
		// As we don’t have a real DB get operation, the only observable effect is that
		// the save operation was triggered.
		verify(this.saveDummyPort, times(2)).save(anyString()); // "First insert", "Second insert"
	}

	@Test
	@DisplayName("When creating a reservation for the second time, then it should fail and persist once with 'Third insert'")
	void shouldFailOnSecondReservationAttempt() {
		// --- GIVEN ---
		final UUID roomId = UUID.randomUUID();
		final UUID guestId = UUID.randomUUID();
		final Date checkIn = new Date();
		final Date checkOut = new Date(checkIn.getTime() + 2 * 24 * 60 * 60 * 1000);

		// --- WHEN (first call to initialize flag) ---
		this.createReservationUseCase.createReservation(roomId, guestId, checkIn, checkOut);

		// --- THEN (second call should throw) ---
		assertThrows(RuntimeException.class,
				() -> this.createReservationUseCase.createReservation(roomId, guestId, checkIn, checkOut),
				"💥 Simulated failure on second call");

		// --- AND THEN (verify side-effect) ---
		verify(this.saveDummyPort, times(1)).save("Third insert");
	}

	@Test
	@DisplayName("Full chain: validation should fail when check-in after check-out")
	void shouldFailValidationWithInvalidDates() {
		// --- GIVEN ---
		final UUID roomId = UUID.randomUUID();
		final UUID guestId = UUID.randomUUID();
		final Date checkIn = new Date();
		final Date checkOut = new Date(checkIn.getTime() - 1000); // check-in after check-out

		// --- WHEN / THEN ---
		assertThrows(IllegalArgumentException.class,
				() -> this.createReservationUseCase.createReservation(roomId, guestId, checkIn, checkOut));
	}

	@Test
	@DisplayName("Full chain: validation should fail when any required input is null (roomId, guestId, checkIn, checkOut)")
	void shouldFailValidationWhenAnyRequiredInputIsNull() {
		// --- GIVEN (common valid base) ---
		final UUID baseRoomId = UUID.randomUUID();
		final UUID baseGuestId = UUID.randomUUID();
		final Date baseCheckIn = new Date();
		final Date baseCheckOut = new Date(baseCheckIn.getTime() + 1_000L);

		// “parametrized” loop without restarting the Spring context
		final Object[][] cases = new Object[][]{{null, baseGuestId, baseCheckIn, baseCheckOut, "roomId=null"},
				{baseRoomId, null, baseCheckIn, baseCheckOut, "guestId=null"},
				{baseRoomId, baseGuestId, null, baseCheckOut, "checkIn=null"},
				{baseRoomId, baseGuestId, baseCheckIn, null, "checkOut=null"}};

		for (final Object[] c : cases) {
			// --- GIVEN (case) ---
			final UUID roomId = (UUID) c[0];
			final UUID guestId = (UUID) c[1];
			final Date checkIn = (Date) c[2];
			final Date checkOut = (Date) c[3];
			final String label = (String) c[4];

			// --- WHEN / THEN ---
			assertThrows(IllegalArgumentException.class,
					() -> this.createReservationUseCase.createReservation(roomId, guestId, checkIn, checkOut),
					"Expected validation failure for case: " + label);
		}
	}
}
