package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.repository;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.common.readmodel.datamodel.ReservationMongoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationMongoRepository extends MongoRepository<ReservationMongoDocument, UUID> {

}
