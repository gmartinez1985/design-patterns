package com.germarna.patterns.observer.hexagonalddd.application.decorator;

import com.germarna.patterns.observer.hexagonalddd.domain.port.in.usecase.CreateReservationUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransactionalCreateReservationDecoratorTest {

	@Mock
	private CreateReservationUseCase delegate;

	@Test
	void createReservation_withTransaction_callsDelegate() {
		final TransactionalCreateReservationDecorator decorator = new TransactionalCreateReservationDecorator(
				this.delegate);

		final UUID roomId = UUID.randomUUID();
		final UUID guestId = UUID.randomUUID();
		final Date checkIn = new Date();
		final Date checkOut = new Date();

		decorator.createReservation(roomId, guestId, checkIn, checkOut);

		verify(this.delegate, times(1)).createReservation(roomId, guestId, checkIn, checkOut);
	}
}
