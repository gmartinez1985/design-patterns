package com.germarna.patterns.decorator.hexagonalddd.config.usecase;

import com.germarna.patterns.decorator.hexagonalddd.adapter.in.usecase.decorator.*;
import com.germarna.patterns.decorator.hexagonalddd.application.port.in.CreateReservationUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class CreateReservationUseCaseConfigTest {

	@Autowired
	private CreateReservationUseCase createReservationUseCase;

	// @Test
	// void shouldAssembleDecoratorsInExpectedOrder() {
	// // nivel más externo debería ser Authorization
	// assertThat(this.createReservationUseCase).isInstanceOf(AuthorizationCreateReservationUseCaseDecorator.class);
	//
	// final var auth = (AuthorizationCreateReservationUseCaseDecorator)
	// this.createReservationUseCase;
	// assertThat(auth.getDelegate()).isInstanceOf(ValidationCreateReservationUseCaseDecorator.class);
	//
	// final var validation = (ValidationCreateReservationUseCaseDecorator)
	// auth.getDelegate();
	// assertThat(validation.getDelegate()).isInstanceOf(MetricsCreateReservationUseCaseDecorator.class);
	//
	// final var metrics = (MetricsCreateReservationUseCaseDecorator)
	// validation.getDelegate();
	// assertThat(metrics.getDelegate()).isInstanceOf(LoggingCreateReservationUseCaseDecorator.class);
	//
	// final var logging = (LoggingCreateReservationUseCaseDecorator)
	// metrics.getDelegate();
	// assertThat(logging.getDelegate()).isInstanceOf(TransactionalCreateReservationUseCaseDecorator.class);
	//
	// final var transactional = (TransactionalCreateReservationUseCaseDecorator)
	// logging.getDelegate();
	// assertThat(transactional.getDelegate()).isInstanceOf(CreateReservationService.class);
	// }

	@Test
	void decorators_are_wired_in_expected_order() throws Exception {
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

		final var nothing = this.getDelegate(transactional);
		assertNull(nothing);

	}

	private Object getDelegate(Object target) throws Exception {
		Class<?> clazz = target.getClass();
		while (clazz != null) {
			try {
				final Field field = clazz.getDeclaredField("delegate");
				field.setAccessible(true);
				return field.get(target);
			} catch (final NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			}
		}
		throw new NoSuchFieldException("No delegate field found in " + target.getClass());
	}
}
