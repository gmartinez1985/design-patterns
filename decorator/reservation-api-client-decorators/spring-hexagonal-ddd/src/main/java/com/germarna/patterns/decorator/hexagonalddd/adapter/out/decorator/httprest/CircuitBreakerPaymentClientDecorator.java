package com.germarna.patterns.decorator.hexagonalddd.adapter.out.decorator.httprest;

import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class CircuitBreakerPaymentClientDecorator implements PaymentClient {

	private final PaymentClient delegate;

	@Autowired
	private CircuitBreakerRegistry registry;

	public CircuitBreakerPaymentClientDecorator(PaymentClient delegate) {
		this.delegate = delegate;
	}

	@Override
	@CircuitBreaker(name = "paymentCircuitBreaker", fallbackMethod = "fallbackPay")
	public boolean pay(UUID reservationId, double amount) {
		final io.github.resilience4j.circuitbreaker.CircuitBreaker cb = this.registry
				.circuitBreaker("paymentCircuitBreaker");
		System.out.println("🔌 CircuitBreaker State: " + cb.getState());
		return this.delegate.pay(reservationId, amount);
	}

	public boolean fallbackPay(UUID reservationId, double amount, Throwable throwable) {
		final io.github.resilience4j.circuitbreaker.CircuitBreaker cb = this.registry
				.circuitBreaker("paymentCircuitBreaker");
		System.out.println("⚠️ Circuit breaker fallback: payment failed for reservation " + reservationId + " due to "
				+ throwable.getMessage());
		System.out.println("📊 CircuitBreaker Failure Count: " + cb.getMetrics().getNumberOfFailedCalls());
		return false;
	}
}
