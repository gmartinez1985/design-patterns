package com.germarna.patterns.decorator.hexagonalddd.config.usecase;

import com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.*;
import com.germarna.patterns.decorator.hexagonalddd.application.domain.service.CreateReservationService;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.CreateReservationUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AopTestUtils;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = "app.startup-runner.enabled=false")
class CreateReservationUseCaseConfigTest {

	@Autowired
	private CreateReservationUseCase createReservationUseCase;

	@Test
	void testDecoratorsWiringOrder() throws Exception {
		final var auth = this.createReservationUseCase;
		assertThat(auth).isInstanceOf(AuthorizationCreateReservationUseCaseDecorator.class);

		final var validation = this.getDelegate(auth);
		assertThat(validation).isInstanceOf(ValidationCreateReservationUseCaseDecorator.class);

		final var metrics = this.getDelegate(validation);
		assertThat(metrics).isInstanceOf(MetricsCreateReservationUseCaseDecorator.class);

		final var logging = this.getDelegate(metrics);
		assertThat(logging).isInstanceOf(LoggingCreateReservationUseCaseDecorator.class);

		final var transactional = this.getDelegate(logging);
		assertThat(transactional).isInstanceOf(TransactionalCreateReservationUseCaseDecorator.class);

		final var service = this.getDelegate(transactional);
		assertThat(service).isInstanceOf(CreateReservationService.class);

		assertThrows(NoSuchFieldException.class, () -> this.getDelegate(service));
	}

	private Object getDelegate(Object target) throws Exception {
		final Object current = this.unwrapProxy(target);

		Class<?> clazz = current.getClass();
		while (clazz != null) {
			try {
				final Field field = clazz.getDeclaredField("delegate");
				field.setAccessible(true);
				final Object value = field.get(current);
				return value == null ? null : this.unwrapProxy(value);
			} catch (final NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			}
		}
		throw new NoSuchFieldException("No delegate field found in " + current.getClass());
	}

	private Object unwrapProxy(Object candidate) throws Exception {
		return AopTestUtils.getTargetObject(candidate);
	}
}
