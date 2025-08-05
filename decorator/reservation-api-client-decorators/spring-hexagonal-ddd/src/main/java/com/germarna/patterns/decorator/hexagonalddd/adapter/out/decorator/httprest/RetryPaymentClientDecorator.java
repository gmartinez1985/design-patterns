package com.germarna.patterns.decorator.hexagonalddd.adapter.out.decorator.httprest;

import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import io.github.resilience4j.retry.annotation.Retry;

import java.util.UUID;

public class RetryPaymentClientDecorator implements PaymentClient {

	private final PaymentClient delegate;

	public RetryPaymentClientDecorator(PaymentClient delegate) {
		this.delegate = delegate;
	}

	@Override
	@Retry(name = "paymentRetry")
	public boolean pay(UUID reservationId, double amount) {
		return this.delegate.pay(reservationId, amount);
	}
}
