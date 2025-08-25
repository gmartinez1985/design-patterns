package com.germarna.patterns.decorator.hexagonalddd.integration;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.httprest.HttpRestPaymentClientAdapter;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.usecase.CreateReservationUseCase;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CreateReservationIT {

	@Autowired
	private CreateReservationUseCase createReservationUseCase;

	@MockitoSpyBean
	private PaymentClient paymentClient;

	@MockitoBean
	private CommandLineRunner startupRunner;

	@MockitoSpyBean
	private HttpRestPaymentClientAdapter httpRestPaymentClientAdapter;

	@Autowired
	private CircuitBreakerRegistry circuitBreakerRegistry;

	// --- Configuración de tests --------------------------------------------
	private static final long TWO_DAYS_MS = 2 * 24 * 60 * 60 * 1000L;
	private static final int RETRY_ATTEMPTS = 2;
	private static final Duration MAX_WAIT = Duration.ofSeconds(15);
	private static final Duration POLL_INTERVAL = Duration.ofMillis(500);
	private static final Duration CACHE_AWAIT = Duration.ofSeconds(5);

	private UUID roomId;
	private UUID guestId;
	private Date checkIn;
	private Date checkOut;

	@BeforeEach
	void initBaseData() {
		this.roomId = UUID.randomUUID();
		this.guestId = UUID.randomUUID();
		this.checkIn = new Date();
		this.checkOut = new Date(this.checkIn.getTime() + TWO_DAYS_MS);
	}

	// --- Helpers ------------------------------------------------------------
	private boolean attemptReservation(UUID reservationId) {
		return this.createReservationUseCase.createReservation(this.roomId, this.guestId, this.checkIn, this.checkOut,
				reservationId);
	}

	private void safeAttempt(UUID reservationId) {
		try {
			this.attemptReservation(reservationId);
		} catch (final RuntimeException ignored) {
		}
	}

	// --- Tests: Retry / Circuit Breaker ------------------------------------
	@Test
	@DisplayName("Eventually succeeds despite payment failures")
	void shouldEventuallyCreateReservationDespiteFailures() {
		await().atMost(MAX_WAIT).pollInterval(POLL_INTERVAL).ignoreExceptions().untilAsserted(() -> {
			// --- GIVEN ---
			final UUID reservationId = UUID.randomUUID();

			// --- WHEN / THEN ---
			assertTrue(this.attemptReservation(reservationId),
					"Reservation should eventually succeed despite failures");
		});
	}

	@Test
	@DisplayName("Reservation fails immediately if payment fails first")
	void shouldFailReservationCreationDueToPayment() {
		// --- GIVEN ---
		final UUID reservationId = UUID.randomUUID();

		// --- WHEN / THEN ---
		final RuntimeException exception = assertThrows(RuntimeException.class,
				() -> this.attemptReservation(reservationId));

		// --- AND THEN ---
		verify(this.httpRestPaymentClientAdapter, times(RETRY_ATTEMPTS)).pay(any(UUID.class), anyDouble());
		assertTrue(exception.getMessage().contains("Payment failed"), "Exception should indicate payment failure");
	}

	@Test
	@DisplayName("Circuit breaker tracks repeated failures")
	void shouldTriggerFallbackOnPaymentFailure() {
		// --- GIVEN ---
		final UUID reservationId = UUID.randomUUID();

		// --- WHEN / THEN ---
		assertThrows(RuntimeException.class, () -> this.attemptReservation(reservationId));

		// --- AND THEN check circuit breaker metrics ---
		final var cb = this.circuitBreakerRegistry.circuitBreaker("paymentCircuitBreaker");
		assertTrue(cb.getMetrics().getNumberOfFailedCalls() > 0, "Circuit breaker should track failed calls");
	}

	@Test
	@DisplayName("Circuit breaker eventually opens after repeated failures")
	void shouldEventuallyOpenCircuitBreakerAfterRepeatedFailures() {
		// --- GIVEN ---
		final var cb = this.circuitBreakerRegistry.circuitBreaker("paymentCircuitBreaker");
		cb.reset();

		// --- WHEN / THEN ---
		await().atMost(MAX_WAIT).pollInterval(POLL_INTERVAL).ignoreExceptions().untilAsserted(() -> {
			final UUID res1 = UUID.randomUUID();
			final UUID res2 = UUID.randomUUID();
			final UUID res3 = UUID.randomUUID();

			assertThrows(RuntimeException.class, () -> this.attemptReservation(res1));
			assertThrows(RuntimeException.class, () -> this.attemptReservation(res2));
			assertThrows(RuntimeException.class, () -> this.attemptReservation(res3));

			assertSame(cb.getState(), CircuitBreaker.State.OPEN,
					"Circuit breaker should be OPEN after repeated failures");
		});
	}

	// --- Tests: Cache -------------------------------------------------------
	@Test
	@DisplayName("Cache prevents multiple REST calls for repeated reservation")
	void shouldHitCacheOnSecondAttempt() {
		// --- GIVEN ---
		final UUID reservationId = UUID.randomUUID();

		// --- WHEN (first attempt, may fail) ---
		this.safeAttempt(reservationId);

		// --- WHEN (second attempt, should hit cache) ---
		this.safeAttempt(reservationId);

		// --- THEN ---
		await().atMost(CACHE_AWAIT).untilAsserted(() -> verify(this.httpRestPaymentClientAdapter, times(RETRY_ATTEMPTS))
				.pay(any(UUID.class), anyDouble()));
	}
}
