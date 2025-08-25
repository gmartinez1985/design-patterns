package com.germarna.patterns.decorator.hexagonalddd.adapter.out.httprest.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResiliencePaymentClientDecoratorTest {

	private static final UUID RESERVATION_ID = UUID.randomUUID();
	private static final double AMOUNT = 123.45d;

	@Mock
	private PaymentClient delegate;

	@Mock
	private CircuitBreakerRegistry registry;

	@Mock
	private CircuitBreaker circuitBreaker;

	@InjectMocks
	private ResiliencePaymentClientDecorator resiliencePaymentClientDecorator;

	@BeforeEach
	void setUp() {
		this.resiliencePaymentClientDecorator.setRegistry(this.registry);

		when(this.registry.circuitBreaker("paymentCircuitBreaker")).thenReturn(this.circuitBreaker);
		when(this.circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);
	}

	@Test
	void shouldDelegateAndPropagateFalse() {
        when(this.delegate.pay(RESERVATION_ID, AMOUNT)).thenReturn(false);

        final boolean result = this.resiliencePaymentClientDecorator.pay(RESERVATION_ID, AMOUNT);

        verify(this.delegate, times(1)).pay(RESERVATION_ID, AMOUNT);
        verifyNoMoreInteractions(this.delegate);
        assertThat(result).isFalse();
    }

	@Test
	void shouldDelegateAndPropagateTrue() {
        when(this.delegate.pay(RESERVATION_ID, AMOUNT)).thenReturn(true);

        final boolean result = this.resiliencePaymentClientDecorator.pay(RESERVATION_ID, AMOUNT);

        verify(this.delegate, times(1)).pay(RESERVATION_ID, AMOUNT);
        verifyNoMoreInteractions(this.delegate);
        assertThat(result).isTrue();
    }
}
