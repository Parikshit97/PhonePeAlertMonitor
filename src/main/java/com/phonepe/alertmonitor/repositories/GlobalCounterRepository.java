package com.phonepe.alertmonitor.repositories;


import com.phonepe.alertmonitor.entities.GlobalCounter;
import com.phonepe.alertmonitor.enums.EventType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GlobalCounterRepository extends MongoRepository<GlobalCounter, String> {

    Optional<GlobalCounter> findByClientAndEventType(String client, EventType eventType);

}
