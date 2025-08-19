package com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.port.in.CreateReservationUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValidationCreateReservationUseCaseDecoratorTest {
	@Mock
	private CreateReservationUseCase delegate;
	@InjectMocks
	private ValidationCreateReservationUseCaseDecorator decorator;

	@Test
	@DisplayName("Should call delegate when input is valid")
	void shouldCallDelegateWhenInputIsValid() {
		final UUID roomId = UUID.randomUUID();
		final UUID guestId = UUID.randomUUID();
		final Date checkIn = new Date();
		final Date checkOut = new Date(checkIn.getTime() + 24 * 60 * 60 * 1000); // +1 day

		final UUID expectedReservationId = UUID.randomUUID();
		when(this.delegate.createReservation(roomId, guestId, checkIn, checkOut)).thenReturn(expectedReservationId);

		final UUID actualReservationId = this.decorator.createReservation(roomId, guestId, checkIn, checkOut);

		assertEquals(expectedReservationId, actualReservationId);
		verify(this.delegate, times(1)).createReservation(roomId, guestId, checkIn, checkOut);
	}

	@Test
	@DisplayName("Should throw exception if any input is null")
	void shouldThrowIfAnyInputIsNull() {
		final UUID roomId = UUID.randomUUID();
		final UUID guestId = UUID.randomUUID();
		final Date checkIn = new Date();
		final Date checkOut = new Date(checkIn.getTime() + 24 * 60 * 60 * 1000);

		assertThrows(IllegalArgumentException.class,
				() -> this.decorator.createReservation(null, guestId, checkIn, checkOut));

		assertThrows(IllegalArgumentException.class,
				() -> this.decorator.createReservation(roomId, null, checkIn, checkOut));

		assertThrows(IllegalArgumentException.class,
				() -> this.decorator.createReservation(roomId, guestId, null, checkOut));

		assertThrows(IllegalArgumentException.class,
				() -> this.decorator.createReservation(roomId, guestId, checkIn, null));

		verifyNoInteractions(this.delegate);
	}

	@Test
	@DisplayName("Should throw exception if check-in is not before check-out")
	void shouldThrowIfCheckInAfterCheckOut() {
		final UUID roomId = UUID.randomUUID();
		final UUID guestId = UUID.randomUUID();
		final Date checkIn = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000); // tomorrow
		final Date checkOut = new Date(); // today

		assertThrows(IllegalArgumentException.class,
				() -> this.decorator.createReservation(roomId, guestId, checkIn, checkOut));

		verifyNoInteractions(this.delegate);
	}
}
