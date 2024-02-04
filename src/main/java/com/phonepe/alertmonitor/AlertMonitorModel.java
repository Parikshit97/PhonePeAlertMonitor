package com.phonepe.alertmonitor;

import com.phonepe.alertmonitor.alertConfigs.AlertConfig;
import com.phonepe.alertmonitor.entities.ClientConfiguration;
import com.phonepe.alertmonitor.enums.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AlertMonitorModel {

    private final ClientConfigurationRepository clientConfigurationRepository;

    @Autowired
    public AlertMonitorModel(ClientConfigurationRepository clientConfigurationRepository) {
        this.clientConfigurationRepository = clientConfigurationRepository;
    }

    public void saveClientConfiguration(ClientConfiguration clientConfiguration) {
        clientConfigurationRepository.save(clientConfiguration);
    }

    public ClientConfiguration getClientConfiguration(String client, EventType eventType, AlertConfig alertType) {
        Optional<ClientConfiguration> optionalClientConfiguration = clientConfigurationRepository
                .findByClientAndEventTypeAndAlertConfig(client, eventType, alertType);
        return optionalClientConfiguration.orElse(null);
    }

    public void updateClientConfiguration(AlertConfig newAlertConfig, ClientConfiguration existingClientConfiguration) {
        existingClientConfiguration.setAlertConfig(newAlertConfig);
        clientConfigurationRepository.save(existingClientConfiguration);
    }

    public ClientConfiguration getByClientAndEventType(String client, EventType eventType) {
        Optional<ClientConfiguration> optionalClientConfiguration = clientConfigurationRepository
                .findByClientAndEventType(client, eventType);
        return optionalClientConfiguration.orElse(null);
    }
}
