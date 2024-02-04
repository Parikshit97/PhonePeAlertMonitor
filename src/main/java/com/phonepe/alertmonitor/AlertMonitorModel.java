package com.phonepe.alertmonitor;

import com.phonepe.alertmonitor.alertConfigs.AlertConfig;
import com.phonepe.alertmonitor.alertConfigs.AlertConfigItem;
import com.phonepe.alertmonitor.dispatchConfigs.DispatchStrategy;
import com.phonepe.alertmonitor.entities.ClientConfiguration;
import com.phonepe.alertmonitor.entities.GlobalCounter;
import com.phonepe.alertmonitor.enums.EventType;
import com.phonepe.alertmonitor.exceptionRequests.ExceptionRaise;
import com.phonepe.alertmonitor.service.AlertMonitorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class AlertMonitorModel {

    private static final Logger logger = LogManager.getLogger(AlertMonitorModel.class);
    private final ClientConfigurationRepository clientConfigurationRepository;
    private final GlobalCounterRepository globalCounterRepository;

    @Autowired
    public AlertMonitorModel(ClientConfigurationRepository clientConfigurationRepository,
                             GlobalCounterRepository globalCounterRepository) {
        this.clientConfigurationRepository = clientConfigurationRepository;
        this.globalCounterRepository = globalCounterRepository;
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

    public void updateGlobalCounter(ExceptionRaise exceptionRaise) {
        Optional<GlobalCounter> optionalGlobalCounter = globalCounterRepository.findByClientAndEventType(
                exceptionRaise.getClient(),
                exceptionRaise.getEventType());
        GlobalCounter globalCounter = optionalGlobalCounter.orElse(null);
        ClientConfiguration clientConfiguration = getByClientAndEventType(globalCounter.getClient(),
                globalCounter.getEventType());
        Long currentEpochSecond = Instant.now().getEpochSecond();
        logStart(clientConfiguration);
        if(globalCounter!=null) {
            Long window = currentEpochSecond - globalCounter.getTimeNow();
            if(window <= globalCounter.getWindowSizeInSecs()) {
                if (globalCounter.getGc() + 1 >= globalCounter.getCount()) {
                    // threshold breached
                    logThreshold(globalCounter);

                    if (clientConfiguration != null) {
                        clientConfiguration.getDispatchStrategyList().stream().forEach(dispatchStrategy -> {
                            switch (dispatchStrategy.getType()) {
                                case CONSOLE:
                                    logConsoleDispatch(dispatchStrategy);
                                    break;
                                case EMAIL:
                                    logEmailDispatch();
                                    break;
                            }
                        });
                    }
                }
            }

            if(currentEpochSecond - globalCounter.getTimeUpdated() >= globalCounter.getWindowSizeInSecs()){
                // threshold did not breach
                // check window, if exceeded, update the gc to 1 and timenow to current seconds
                globalCounter.setTimeUpdated(globalCounter.getTimeNow());
                globalCounter.setTimeNow(currentEpochSecond);
                globalCounter.setGc(1L);
                globalCounterRepository.save(globalCounter);
            } else{
                // Increment gc and save
                globalCounter.setGc(globalCounter.getGc() + 1);
                globalCounterRepository.save(globalCounter);
            }
        }
        logEnd(clientConfiguration);
    }

    private void logStart(ClientConfiguration clientConfiguration) {
        logger.info("[INFO] MonitoringService: Client {} {} {} starts", clientConfiguration.getClient(),
                clientConfiguration.getEventType(),
                clientConfiguration.getAlertConfig().getType());
    }

    private void logEnd(ClientConfiguration clientConfiguration) {
        logger.info("[INFO] MonitoringService: Client {} {} {} ends", clientConfiguration.getClient(),
                clientConfiguration.getEventType(),
                clientConfiguration.getAlertConfig().getType());
    }

    private void logConsoleDispatch(DispatchStrategy dispatchStrategy) {
        logger.info("[INFO] AlertingService: \u001B[1mDispatching to Console\u001B[0m");
        logger.warn("[WARN] Alert: \u001B[1m`" + dispatchStrategy.getMessage() + "`\u001B[0m");
    }

    private void logEmailDispatch() {
        logger.info("[INFO] AlertingService: \u001B[1mDispatching an Email\u001B[0m");
    }

    private void logThreshold(GlobalCounter globalCounter) {
        logger.info("[INFO] MonitoringService: Client {} {} \u001B[1mthreshold breached\u001B[0m",
                globalCounter.getClient(),
                globalCounter.getEventType());
    }
}
