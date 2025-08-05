package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence;

import com.germarna.patterns.decorator.hexagonalddd.application.port.out.SaveDummyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DummyPersistenceAdapter implements SaveDummyPort {
	private final DummyRepository dummyRepository;
	@Override
	public void save(String dummyString) {
		this.dummyRepository.save(new DummyJpaEntity(dummyString));
	}
}
