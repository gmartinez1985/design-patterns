package com.germarna.patterns.observer.hexagonalddd.config.helpers;

import org.springframework.test.util.AopTestUtils;

import java.lang.reflect.Field;

public final class DecoratorTestUtils {
	public static Object getDelegate(Object target) throws Exception {
		final Object current = unwrapProxy(target);

		Class<?> clazz = current.getClass();
		while (clazz != null) {
			try {
				final Field field = clazz.getDeclaredField("delegate");
				field.setAccessible(true);
				final Object value = field.get(current);
				return value == null ? null : unwrapProxy(value);
			} catch (final NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			}
		}
		throw new NoSuchFieldException("No delegate field found in " + current.getClass());
	}

	private static Object unwrapProxy(Object candidate) throws Exception {
		return AopTestUtils.getTargetObject(candidate);
	}
}
