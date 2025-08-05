package com.germarna.patterns.decorator.hexagonalddd.adapter.out.decorator.httprest;

import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;

import java.util.UUID;

public class HttpRestPaymentClientAdapter implements PaymentClient {

	private int attempt = 0;

	@Override
	public boolean pay(UUID reservationId, double amount) {
		this.attempt++;
		System.out.println("🧪HttpRestPaymentClientAdapter: Simulating REST payment for reservation " + reservationId
				+ " with amount " + amount);

		if (this.attempt <= 6) {
			System.out.println(
					"💥HttpRestPaymentClientAdapter: Simulating failure on payment (attempt " + this.attempt + ")");
			throw new RuntimeException("Simulated payment error (attempt " + this.attempt + ")");
		}

		System.out.println("HttpRestPaymentClientAdapter: Payment SUCCESSFUL on attempt " + this.attempt);
		return true;
	}
}
