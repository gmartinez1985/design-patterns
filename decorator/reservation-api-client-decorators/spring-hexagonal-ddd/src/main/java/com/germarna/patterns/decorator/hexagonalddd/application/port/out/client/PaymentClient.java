package com.germarna.patterns.decorator.hexagonalddd.application.port.out.client;

import java.util.UUID;

public interface PaymentClient {
	boolean pay(UUID reservationId, double amount);

}
