package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DummyJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String recordValue;

	public DummyJpaEntity() {
	}

	public DummyJpaEntity(String recordValue) {
		this.recordValue = recordValue;
	}
}
