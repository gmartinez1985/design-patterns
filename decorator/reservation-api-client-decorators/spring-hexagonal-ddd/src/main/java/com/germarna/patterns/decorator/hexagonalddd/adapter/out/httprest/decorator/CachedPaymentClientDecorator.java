package com.germarna.patterns.decorator.hexagonalddd.adapter.out.httprest.decorator;

import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import org.springframework.cache.annotation.Cacheable;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CachedPaymentClientDecorator extends BasePaymentClientDecorator {

	private final AtomicInteger callCount = new AtomicInteger();

	public CachedPaymentClientDecorator(PaymentClient delegate) {
		super(delegate);
	}

	@Override
	@Cacheable(value = "payments", key = "#reservationId")
	public boolean pay(UUID reservationId, double amount) {
		final int currentCall = this.callCount.incrementAndGet();
		System.out.println("⬇️ Cache MISS for reservation " + reservationId + " (call #" + currentCall + ")");
		return super.pay(reservationId, amount);
	}
}
