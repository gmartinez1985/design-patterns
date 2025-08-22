package com.germarna.patterns.decorator.hexagonalddd.application.domain.service;

import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateReservationServiceTest {

	@Mock
	private PaymentClient paymentClient;

	@InjectMocks
	private CreateReservationService service;

	private UUID roomId;
	private UUID guestId;
	private UUID reservationId;
	private Date checkIn;

	@BeforeEach
	void setUp() {
		this.roomId = UUID.randomUUID();
		this.guestId = UUID.randomUUID();
		this.reservationId = UUID.randomUUID();
		this.checkIn = new Date();
	}

	private Date plusDays(int days) {
		return new Date(this.checkIn.getTime() + TimeUnit.DAYS.toMillis(days));
	}

	@Test
	void shouldCreateReservation_whenPaymentSucceeds() {
		// --- GIVEN ---
		final Date checkOut = this.plusDays(3); // 3 nights
		final double expectedPrice = 3 * 100.0;

		when(this.paymentClient.pay(eq(this.reservationId), eq(expectedPrice))).thenReturn(true);

		// --- WHEN ---
		final boolean result = this.service.createReservation(this.roomId, this.guestId, this.checkIn, checkOut,
				this.reservationId);

		// --- THEN ---
		assertTrue(result, "Reservation should succeed if payment is successful");
		verify(this.paymentClient, times(1)).pay(this.reservationId, expectedPrice);
		verifyNoMoreInteractions(this.paymentClient);
	}

	@Test
	void shouldThrowException_whenPaymentFails() {
		// --- GIVEN ---
		final Date checkOut = this.plusDays(2); // 2 nights
		final double expectedPrice = 2 * 100.0;

		when(this.paymentClient.pay(eq(this.reservationId), eq(expectedPrice))).thenReturn(false);

		// --- WHEN & THEN ---
		final RuntimeException ex = assertThrows(RuntimeException.class, () -> this.service
				.createReservation(this.roomId, this.guestId, this.checkIn, checkOut, this.reservationId));

		assertEquals("Payment failed. Reservation not created.", ex.getMessage());
		verify(this.paymentClient, times(1)).pay(this.reservationId, expectedPrice);
		verifyNoMoreInteractions(this.paymentClient);
	}

	@Test
	void shouldHandleZeroNightsReservation() {
		// --- GIVEN ---
		final Date checkOut = this.checkIn; // same day → 0 nights
		final double expectedPrice = 0.0;

		when(this.paymentClient.pay(eq(this.reservationId), eq(expectedPrice))).thenReturn(true);

		// --- WHEN ---
		final boolean result = this.service.createReservation(this.roomId, this.guestId, this.checkIn, checkOut,
				this.reservationId);

		// --- THEN ---
		assertTrue(result, "Reservation of 0 nights should be allowed and free");
		verify(this.paymentClient, times(1)).pay(this.reservationId, expectedPrice);
		verifyNoMoreInteractions(this.paymentClient);
	}
}
