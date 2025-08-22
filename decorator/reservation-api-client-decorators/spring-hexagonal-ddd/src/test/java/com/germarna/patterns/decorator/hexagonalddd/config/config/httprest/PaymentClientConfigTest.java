package com.germarna.patterns.decorator.hexagonalddd.config.config.httprest;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.httprest.decorator.CachedPaymentClientDecorator;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.httprest.decorator.ResiliencePaymentClientDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import com.germarna.patterns.decorator.hexagonalddd.testutil.DecoratorTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class PaymentClientConfigTest {

	@Autowired
	private PaymentClient paymentClient;

	@MockitoBean
	private CommandLineRunner startupRunner;

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
