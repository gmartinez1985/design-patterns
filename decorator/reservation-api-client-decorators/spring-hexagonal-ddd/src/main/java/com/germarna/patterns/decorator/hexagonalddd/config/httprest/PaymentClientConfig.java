package com.germarna.patterns.decorator.hexagonalddd.config.httprest;

import com.germarna.patterns.decorator.hexagonalddd.adapter.out.decorator.httprest.CachedPaymentClientDecorator;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.decorator.httprest.CircuitBreakerPaymentClientDecorator;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.decorator.httprest.HttpRestPaymentClientAdapter;
import com.germarna.patterns.decorator.hexagonalddd.adapter.out.decorator.httprest.RetryPaymentClientDecorator;
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
	public PaymentClient retryPaymentClientDecorator(
			@Qualifier("httpRestPaymentClientAdapter") PaymentClient delegate) {
		return new RetryPaymentClientDecorator(delegate);
	}

	@Bean
	public PaymentClient circuitBreakerPaymentClientDecorator(
			@Qualifier("retryPaymentClientDecorator") PaymentClient delegate,
			@Autowired CircuitBreakerRegistry circuitBreakerRegistry) {
		return new CircuitBreakerPaymentClientDecorator(delegate);
	}

	@Bean
	public PaymentClient cachedPaymentClientDecorator(
			@Qualifier("circuitBreakerPaymentClientDecorator") PaymentClient delegate) {
		return new CachedPaymentClientDecorator(delegate);
	}

	@Bean
	@Primary
	public PaymentClient createPaymentClient(@Qualifier("cachedPaymentClientDecorator") PaymentClient delegate) {
		return delegate;
	}
}
