package com.germarna.patterns.decorator.hexagonalddd.config.support;
public class UseCaseBuilder<T> {
	private T component;

	private UseCaseBuilder(T core) {
		this.component = core;
	}

	public static <T> UseCaseBuilder<T> wrap(T core) {
		return new UseCaseBuilder<>(core);
	}

	public UseCaseBuilder<T> with(java.util.function.UnaryOperator<T> decorator) {
		this.component = decorator.apply(this.component);
		return this;
	}

	public T build() {
		return this.component;
	}
}
