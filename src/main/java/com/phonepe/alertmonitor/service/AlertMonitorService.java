package com.phonepe.alertmonitor.service;

import com.phonepe.alertmonitor.models.AlertMonitorModel;
import com.phonepe.alertmonitor.alertConfigs.AlertConfig;
import com.phonepe.alertmonitor.alertConfigs.AlertConfigItem;
import com.phonepe.alertmonitor.alertConfigs.AlertConfigList;
import com.phonepe.alertmonitor.entities.ClientConfiguration;
import com.phonepe.alertmonitor.entities.GlobalCounter;
import com.phonepe.alertmonitor.enums.EventType;
import com.phonepe.alertmonitor.exceptionRequests.ExceptionRaise;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
@Service
public class AlertMonitorService {

    private static final Logger logger = LogManager.getLogger(AlertMonitorService.class);

    private AlertMonitorModel alertMonitorModel;

    @Autowired
    public AlertMonitorService(AlertMonitorModel alertMonitorModel){
        this.alertMonitorModel = alertMonitorModel;
    }


    public void saveClientConfigurations(AlertConfigList alertConfigList) {
        List<AlertConfigItem> alertConfigItems = alertConfigList.getAlertConfigList();

        for (AlertConfigItem alertConfigItem : alertConfigItems) {
            ClientConfiguration existingClientConfig = getByClientAndEventType(alertConfigItem.getClient(), alertConfigItem.getEventType());

            if (existingClientConfig != null) {
                alertMonitorModel.updateClientConfiguration(alertConfigItem.getAlertConfig(), existingClientConfig);
            } else {
                ClientConfiguration newClientConfig = convertToClientConfiguration(alertConfigItem);
                alertMonitorModel.saveClientConfiguration(newClientConfig);
            }

            this.initializeGlobalCounters(alertConfigItem);
        }
    }

    private void initializeGlobalCounters(AlertConfigItem alertConfigItem){
        alertMonitorModel.initializeGlobalCounters(createGlobalCountersFromAlertConfigItem(alertConfigItem));
    }

    private GlobalCounter createGlobalCountersFromAlertConfigItem(AlertConfigItem alertConfigItem){
        GlobalCounter globalCounter = new GlobalCounter();
        globalCounter.setClient(alertConfigItem.getClient());
        globalCounter.setEventType(alertConfigItem.getEventType());
        globalCounter.setWindowSizeInSecs(alertConfigItem.getAlertConfig().getWindowSizeInSecs());
        globalCounter.setCount(alertConfigItem.getAlertConfig().getCount());
        globalCounter.setTimeNow(Instant.now().getEpochSecond());
        globalCounter.setTimeUpdated(0L);
        globalCounter.setGc(0L);
        return globalCounter;
    }

    private ClientConfiguration getByClientAndEventType(String client, EventType eventType) {
        return alertMonitorModel.getByClientAndEventType(client, eventType);
    }

    private ClientConfiguration getClientConfiguration(String client, EventType eventType, AlertConfig alertType) {
        return alertMonitorModel.getClientConfiguration(client, eventType, alertType);
    }

    private ClientConfiguration convertToClientConfiguration(AlertConfigItem alertConfigItem) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setClient(alertConfigItem.getClient());
        clientConfiguration.setEventType(alertConfigItem.getEventType());
        clientConfiguration.setAlertConfig(alertConfigItem.getAlertConfig());
        clientConfiguration.setDispatchStrategyList(alertConfigItem.getDispatchStrategyList());
        return clientConfiguration;
    }

    public void raiseException(ExceptionRaise exceptionRaise) {
         ClientConfiguration clientConfiguration = alertMonitorModel.getByClientAndEventType(exceptionRaise.getClient(),
                 exceptionRaise.getEventType());
         switch (clientConfiguration.getAlertConfig().getType()){
             case TUMBLING_WINDOW: alertMonitorModel.updateGlobalCounterTumblingWindow(exceptionRaise); break;
             case SLIDING_WINDOW: alertMonitorModel.updateGlobalCounterSlidingWindow(exceptionRaise); break;
         }

    }
}


