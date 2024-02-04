package com.phonepe.alertmonitor.models;

import com.phonepe.alertmonitor.repositories.ClientConfigurationRepository;
import com.phonepe.alertmonitor.repositories.GlobalCounterRepository;
import com.phonepe.alertmonitor.alertConfigs.AlertConfig;
import com.phonepe.alertmonitor.entities.ClientConfiguration;
import com.phonepe.alertmonitor.entities.GlobalCounter;
import com.phonepe.alertmonitor.enums.EventType;
import com.phonepe.alertmonitor.exceptionRequests.ExceptionRaise;
import com.phonepe.alertmonitor.processEventsImplementations.ProcessSlidingWindow;
import com.phonepe.alertmonitor.processEventsImplementations.ProcessTumblingWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AlertMonitorModel {
    private final ClientConfigurationRepository clientConfigurationRepository;
    private final GlobalCounterRepository globalCounterRepository;
    private final ProcessSlidingWindow processSlidingWindow;
    private final ProcessTumblingWindow processTumblingWindow;


    @Autowired
    public AlertMonitorModel(ClientConfigurationRepository clientConfigurationRepository,
                             GlobalCounterRepository globalCounterRepository,
                             ProcessSlidingWindow processSlidingWindow,
                             ProcessTumblingWindow processTumblingWindow
                            ) {
        this.clientConfigurationRepository = clientConfigurationRepository;
        this.globalCounterRepository = globalCounterRepository;
        this.processSlidingWindow = processSlidingWindow;
        this.processTumblingWindow = processTumblingWindow;
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

    public void initializeGlobalCounters(GlobalCounter globalCounter) {
        globalCounterRepository.save(globalCounter);
    }

    public void updateGlobalCounterTumblingWindow(ExceptionRaise exceptionRaise) {
        processTumblingWindow.updateGlobalCounterSlidingWindow(exceptionRaise);
    }

    public void updateGlobalCounterSlidingWindow(ExceptionRaise exceptionRaise) {
        processSlidingWindow.updateGlobalCounterSlidingWindow(exceptionRaise);
    }

}
