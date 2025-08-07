package com.germarna.patterns.decorator.hexagonalddd.adapter.out.decorator.httprest;

import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BasePaymentClientDecorator implements PaymentClient {

	protected final PaymentClient delegate;

	@Override
	public boolean pay(UUID reservationId, double amount) {
		return this.delegate.pay(reservationId, amount);
	}
}
