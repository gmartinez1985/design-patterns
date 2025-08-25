package com.germarna.patterns.decorator.hexagonalddd.integration;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.httprest.decorator.ResiliencePaymentClientDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class ResiliencePaymentClientDecoratorIT {

	@Autowired
	private ResiliencePaymentClientDecorator decorator;

	@MockitoBean
	private PaymentClient failingDelegate;

	@Autowired
	private CircuitBreakerRegistry circuitBreakerRegistry;

	@MockitoBean
	private CommandLineRunner startupRunner;

	@Test
	@DisplayName("Fallback is triggered and circuit breaker sees failure")
	void shouldTriggerFallbackAndRecordFailure() {
		// GIVEN
		final UUID reservationId = UUID.randomUUID();
		final double amount = 100.0;
		doThrow(new RuntimeException("Simulated failure")).when(this.failingDelegate).pay(reservationId, amount);

		// WHEN
		final boolean result = this.decorator.pay(reservationId, amount);

		// THEN
		assertFalse(result, "Fallback should return false");
		final var cb = this.circuitBreakerRegistry.circuitBreaker("paymentCircuitBreaker");
		assertTrue(cb.getMetrics().getNumberOfFailedCalls() > 0, "Circuit breaker should track failed calls");
	}
}
