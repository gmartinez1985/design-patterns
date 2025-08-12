package com.germarna.patterns.decorator.hexagonalddd.adapter.out.httprest.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class ResiliencePaymentClientDecorator extends BasePaymentClientDecorator {

	@Autowired
	private CircuitBreakerRegistry registry;

	public ResiliencePaymentClientDecorator(PaymentClient delegate) {
		super(delegate);
	}

	@Override
	@CircuitBreaker(name = "paymentCircuitBreaker", fallbackMethod = "fallbackPay")
	@Retry(name = "paymentRetry")
	public boolean pay(UUID reservationId, double amount) {
		final io.github.resilience4j.circuitbreaker.CircuitBreaker cb = this.registry
				.circuitBreaker("paymentCircuitBreaker");
		System.out.println("🔌 CircuitBreaker current state: " + cb.getState());
		return super.pay(reservationId, amount);
	}

	public boolean fallbackPay(UUID reservationId, double amount, Throwable throwable) {
		final io.github.resilience4j.circuitbreaker.CircuitBreaker cb = this.registry
				.circuitBreaker("paymentCircuitBreaker");
		System.out.println("⚠️ Circuit breaker fallback: payment failed for reservation " + reservationId + " due to "
				+ throwable.getMessage());
		System.out.println("📊 CircuitBreaker Failure Count: " + cb.getMetrics().getNumberOfFailedCalls() + " ("
				+ this.printFailureRate(cb.getMetrics()) + "/" + cb.getCircuitBreakerConfig().getFailureRateThreshold()
				+ "%)");
		System.out.println("🔌 CircuitBreaker final State: " + cb.getState());
		return false;
	}

	private String printFailureRate(io.github.resilience4j.circuitbreaker.CircuitBreaker.Metrics metrics) {
		final float failureRate = metrics.getFailureRate();
		return failureRate == -1 ? "-" : String.valueOf(failureRate).concat("%");

	}
}
