package com.phonepe.alertmonitor.service;

import com.phonepe.alertmonitor.AlertMonitorModel;
import com.phonepe.alertmonitor.alertConfigs.AlertConfig;
import com.phonepe.alertmonitor.alertConfigs.AlertConfigItem;
import com.phonepe.alertmonitor.alertConfigs.AlertConfigList;
import com.phonepe.alertmonitor.entities.ClientConfiguration;
import com.phonepe.alertmonitor.enums.EventType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        }
    }

    private void updateClientConfiguration(ClientConfiguration existingConfig, AlertConfigItem newConfigItem) {
        AlertConfig newAlertConfig = newConfigItem.getAlertConfig();

        existingConfig.setEventType(newConfigItem.getEventType());
        existingConfig.setAlertConfig(newAlertConfig);
        existingConfig.setDispatchStrategyList(newConfigItem.getDispatchStrategyList());

//        alertMonitorModel.updateClientConfiguration(existingConfig);
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

    private boolean thresholdBreached(AlertConfigItem alertConfigItem) {
        return alertConfigItem.getAlertConfig().getCount() >= alertConfigItem.getAlertConfig().getWindowSizeInSecs();
    }

    private void logStart(AlertConfigItem alertConfigItem) {
        logger.info("[INFO] MonitoringService: Client {} {} {} starts", alertConfigItem.getClient(), alertConfigItem.getEventType(), alertConfigItem.getAlertConfig().getType());
    }

    private void logEnd(AlertConfigItem alertConfigItem) {
        logger.info("[INFO] MonitoringService: Client {} {} {} ends", alertConfigItem.getClient(), alertConfigItem.getEventType(), alertConfigItem.getAlertConfig().getType());
    }

    private void logThreshold(AlertConfigItem alertConfigItem) {
        logger.info("[INFO] MonitoringService: Client {} {} \u001B[1mthreshold breached\u001B[0m", alertConfigItem.getClient(), alertConfigItem.getEventType());
    }

}


