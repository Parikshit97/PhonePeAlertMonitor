package com.phonepe.alertmonitor;

import com.phonepe.alertmonitor.alertConfigs.AlertConfig;
import com.phonepe.alertmonitor.entities.ClientConfiguration;
import com.phonepe.alertmonitor.enums.EventType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientConfigurationRepository extends MongoRepository<ClientConfiguration, String> {

    Optional<ClientConfiguration> findByClientAndEventTypeAndAlertConfig(String client, EventType eventType, AlertConfig alertConfig);

    Optional<ClientConfiguration> findByClientAndEventType(String client, EventType eventType);
}

