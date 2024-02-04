package com.phonepe.alertmonitor.service;

import com.phonepe.alertmonitor.alertConfigs.AlertConfigItem;
import com.phonepe.alertmonitor.alertConfigs.AlertConfigList;
import com.phonepe.alertmonitor.enums.EventType;
import com.phonepe.alertmonitor.interfaces.ProcessAlertEvents;
import com.phonepe.alertmonitor.processEventsImplementations.ProcessSlidingWindow;
import com.phonepe.alertmonitor.processEventsImplementations.ProcessTumblingWindow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AlertMonitorService {

    private static final Logger logger = LogManager.getLogger(AlertMonitorService.class);
    private static final Integer NUMBER_EVENT_TYPES = EnumSet.allOf(EventType.class).size();
    private ProcessAlertEvents processTumblingWindow;
    private ProcessAlertEvents processSlidingWindow;

    @Autowired
    public AlertMonitorService(ProcessTumblingWindow processTumblingWindow,
                               ProcessSlidingWindow processSlidingWindow){
        this.processTumblingWindow = processTumblingWindow;
        this.processSlidingWindow = processSlidingWindow;
    }


    public AlertConfigList getAlertConfig(AlertConfigList alertConfigList){
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_EVENT_TYPES);
        Optional<List<AlertConfigItem>> alertConfigItems = Optional.ofNullable(alertConfigList.getAlertConfigList());
        if(alertConfigItems.isPresent()){
            alertConfigItems.get().stream().forEach(alertConfigItem -> {
                if(thresholdBreached(alertConfigItem)){
                    switch (alertConfigItem.getAlertConfig().getType()) {
                        case TUMBLING_WINDOW: logStart(alertConfigItem);
                            processTumblingWindow.enqueueAlertConfig(alertConfigItem);
                            logEnd(alertConfigItem);

                            logStart(alertConfigItem);
                            logThreshold(alertConfigItem);
                            processTumblingWindow.processAlertConfigs();
                            logEnd(alertConfigItem);
                            break;

                        case SLIDING_WINDOW:
                            processSlidingWindow.enqueueAlertConfig(alertConfigItem);
                            logThreshold(alertConfigItem);
                            processSlidingWindow.processAlertConfigs();
                    }
                }

            });
        }
        executorService.shutdown();
        return alertConfigList;
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
