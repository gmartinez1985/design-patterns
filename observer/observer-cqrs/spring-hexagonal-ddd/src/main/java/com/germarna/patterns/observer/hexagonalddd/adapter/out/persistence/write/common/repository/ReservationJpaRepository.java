package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.common.repository;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.write.common.entity.ReservationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, UUID> {
}
