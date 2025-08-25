package com.germarna.patterns.decorator.hexagonalddd.config.httprest;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.httprest.decorator.CachedPaymentClientDecorator;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.httprest.decorator.ResiliencePaymentClientDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import com.germarna.patterns.decorator.hexagonalddd.testutil.DecoratorTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "app.startup-runner.enabled=false")
class PaymentClientConfigTest {

	@Autowired
	private PaymentClient paymentClient;

	@Test
	void testDecoratorsWiringOrder() throws Exception {
		final var cached = this.paymentClient;
		assertThat(cached).isInstanceOf(CachedPaymentClientDecorator.class);

		final var resilience = DecoratorTestUtils.getDelegate(cached);
		assertThat(resilience).isInstanceOf(ResiliencePaymentClientDecorator.class);

		final var client = DecoratorTestUtils.getDelegate(resilience);
		assertThat(client).isInstanceOf(PaymentClient.class);

		assertThrows(NoSuchFieldException.class, () -> DecoratorTestUtils.getDelegate(client));
	}
}
