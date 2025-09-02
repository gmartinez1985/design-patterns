package com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.read.repository;

import com.germarna.patterns.observer.hexagonalddd.adapter.out.persistence.read.document.ReservationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationMongoRepository extends MongoRepository<ReservationDocument, UUID> {

}
