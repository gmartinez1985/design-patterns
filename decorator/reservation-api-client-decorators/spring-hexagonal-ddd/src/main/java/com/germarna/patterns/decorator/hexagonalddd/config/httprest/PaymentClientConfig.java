package com.germarna.patterns.decorator.hexagonalddd.config.httprest;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.decorator.httprest.CachedPaymentClientDecorator;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.decorator.httprest.HttpRestPaymentClientAdapter;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.decorator.httprest.ResiliencePaymentClientDecorator;
import com.germarna.patterns.decorator.hexagonalddd.application.port.out.client.PaymentClient;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PaymentClientConfig {

	@Bean
	public PaymentClient httpRestPaymentClientAdapter() {
		return new HttpRestPaymentClientAdapter();
	}

	@Bean
	public PaymentClient resiliencePaymentClientDecorator(
			@Qualifier("httpRestPaymentClientAdapter") PaymentClient delegate,
			@Autowired CircuitBreakerRegistry circuitBreakerRegistry) {
		return new ResiliencePaymentClientDecorator(delegate);
	}

	@Bean
	@Primary
	public PaymentClient cachedPaymentClientDecorator(
			@Qualifier("resiliencePaymentClientDecorator") PaymentClient delegate) {
		return new CachedPaymentClientDecorator(delegate);
	}
}
