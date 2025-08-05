package com.germarna.patterns.decorator.hexagonalddd.adapter.out.decorator.httprest;

import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;

import java.util.UUID;

public abstract class BasePaymentClientDecorator implements PaymentClient {

	protected final PaymentClient delegate;

	protected BasePaymentClientDecorator(PaymentClient delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean pay(UUID reservationId, double amount) {
		return this.delegate.pay(reservationId, amount);
	}
}
