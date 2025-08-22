package com.germarna.patterns.decorator.hexagonalddd.adapter.out.httprest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpRestPaymentClientAdapterTest {

	private static final double AMOUNT = 100.0;

	private HttpRestPaymentClientAdapter adapter;
	private UUID reservationId;

	@BeforeEach
	void setUp() {
		this.adapter = new HttpRestPaymentClientAdapter();
		this.reservationId = UUID.randomUUID();
	}

	private void expectFailure(int attempts) {
		IntStream.rangeClosed(1, attempts).forEach(i -> {
			final RuntimeException ex = assertThrows(RuntimeException.class,
					() -> this.adapter.pay(this.reservationId, AMOUNT));
			assertTrue(ex.getMessage().contains("attempt " + i));
		});
	}

	@Test
	@DisplayName("Fails on first attempt with proper message")
	void shouldFailOnFirstAttempt() {
		final RuntimeException ex = assertThrows(RuntimeException.class,
				() -> this.adapter.pay(this.reservationId, AMOUNT));
		assertTrue(ex.getMessage().contains("attempt 1"));
	}

	@Test
	@DisplayName("Fails on sixth attempt with proper message")
	void shouldFailOnSixthAttempt() {
		this.expectFailure(6);
	}

	@Test
	@DisplayName("Succeeds on seventh attempt")
	void shouldSucceedOnSeventhAttempt() {
		this.expectFailure(6);

		final boolean result = this.adapter.pay(this.reservationId, AMOUNT);
		assertTrue(result, "Seventh attempt should succeed");
	}

	@Test
	@DisplayName("Succeeds on attempts beyond the seventh")
	void shouldAlwaysSucceedAfterSeventhAttempt() {
		this.expectFailure(6);

		// Attempts 7, 8, 9... must succeed
		for (int i = 7; i <= 10; i++) {
			assertTrue(this.adapter.pay(this.reservationId, AMOUNT), "Attempt " + i + " should succeed");
		}
	}
}
