package com.germarna.patterns.decorator.hexagonalddd.adapter.out.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DummyRepository extends JpaRepository<DummyJpaEntity, Long> {
}
