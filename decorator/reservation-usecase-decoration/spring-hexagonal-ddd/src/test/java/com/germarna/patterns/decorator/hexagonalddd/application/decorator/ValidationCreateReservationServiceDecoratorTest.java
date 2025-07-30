package com.germarna.patterns.decorator.hexagonalddd.application.decorator;

import com.germarna.patterns.decorator.hexagonalddd.domain.port.in.usecase.CreateReservationUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class ValidationCreateReservationServiceDecoratorTest {

	@Mock
	private CreateReservationUseCase delegate;

	private ValidationCreateReservationServiceDecorator decorator;

	@BeforeEach
	void setUp() {
		this.decorator = new ValidationCreateReservationServiceDecorator(this.delegate);
	}

	@Test
	void createReservation_withValidInput_delegatesCall() {
		final UUID roomId = UUID.randomUUID();
		final UUID guestId = UUID.randomUUID();
		final Date checkIn = new Date(System.currentTimeMillis());
		final Date checkOut = new Date(System.currentTimeMillis() + 86_400_000); // +1 day

		this.decorator.createReservation(roomId, guestId, checkIn, checkOut);

		verify(this.delegate).createReservation(roomId, guestId, checkIn, checkOut);
	}

	@Test
	void createReservation_withNullParameters_throwsException() {
		final UUID roomId = UUID.randomUUID();
		final UUID guestId = null;
		final Date checkIn = new Date();
		final Date checkOut = new Date();

		final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> this.decorator.createReservation(roomId, guestId, checkIn, checkOut));

		assertTrue(ex.getMessage().contains("must not be null"));
		verifyNoInteractions(this.delegate);
	}

	@Test
	void createReservation_whenCheckInAfterCheckOut_throwsException() {
		final UUID roomId = UUID.randomUUID();
		final UUID guestId = UUID.randomUUID();
		final Date checkIn = new Date(System.currentTimeMillis() + 86_400_000); // tomorrow
		final Date checkOut = new Date(System.currentTimeMillis()); // today

		final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
				() -> this.decorator.createReservation(roomId, guestId, checkIn, checkOut));

		assertTrue(ex.getMessage().contains("Check-in date must be before check-out"));
		verifyNoInteractions(this.delegate);
	}
}
