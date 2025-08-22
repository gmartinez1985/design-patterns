package com.germarna.patterns.decorator.hexagonalddd.integration;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.httprest.decorator.ResiliencePaymentClientDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class ResiliencePaymentClientDecoratorIT {

	@MockitoBean
	private PaymentClient failingPaymentClient; // delegate que siempre falla

	@Autowired
	private ResiliencePaymentClientDecorator resilienceDecorator;

	@Autowired
	private CircuitBreakerRegistry circuitBreakerRegistry;

	@MockitoBean
	private CommandLineRunner startupRunner;

	@Test
	void fallbackIsExecutedWhenDelegateFails() {
		// Arrange: el delegate siempre lanza RuntimeException
		doThrow(new RuntimeException("Simulated payment error")).when(this.failingPaymentClient).pay(UUID.randomUUID(),
				100.0);

		// Spring inyectará ResiliencePaymentClientDecorator con proxy de Resilience4j
		final ResiliencePaymentClientDecorator decorator = new ResiliencePaymentClientDecorator(
				this.failingPaymentClient);
		decorator.setRegistry(this.circuitBreakerRegistry);

		// Act
		final boolean result = decorator.pay(UUID.randomUUID(), 100.0);

		// Assert: fallback debe devolver false
		assertFalse(result, "Fallback should return false when delegate fails");
	}
}
