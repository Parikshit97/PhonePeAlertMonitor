package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AlertMonitorService {

    private static final Logger logger = LogManager.getLogger(AlertMonitorService.class);

    public AlertConfigList getAlertConfig(AlertConfigList alertConfigList){
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        Optional<List<AlertConfigItem>> alertConfigItems = Optional.ofNullable(alertConfigList.getAlertConfigList());
        if(alertConfigItems.isPresent()){
            alertConfigItems.get().forEach(this::processAlertConfigItem);
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();
        return alertConfigList;
    }

    private void processAlertConfigItem(AlertConfigItem alertConfigItem) {
        Optional<List<DispatchStrategy>> dispatchStrategyList = Optional.ofNullable(alertConfigItem.getDispatchStrategyList());
        if(dispatchStrategyList.isPresent()){
            dispatchStrategyList.get().forEach(dispatchStrategy -> {
                logStart(alertConfigItem);
                processDispatchStrategy(dispatchStrategy);
                logEnd(alertConfigItem);
            });
        }
    }

    private void processDispatchStrategy(DispatchStrategy dispatchStrategy) {
        switch(dispatchStrategy.getType()){
            case CONSOLE:
                logConsoleDispatch(dispatchStrategy);
                break;
            case EMAIL:
                logEmailDispatch();
                break;
        }
    }

    private void logStart(AlertConfigItem alertConfigItem) {
        logger.info("[INFO] MonitoringService: Client {} {} {} starts", alertConfigItem.getClient(), alertConfigItem.getEventType(), alertConfigItem.getAlertConfig().getType());
    }

    private void logEnd(AlertConfigItem alertConfigItem) {
        logger.info("[INFO] MonitoringService: Client {} {} {} ends", alertConfigItem.getClient(), alertConfigItem.getEventType(), alertConfigItem.getAlertConfig().getType());
    }

    private void logConsoleDispatch(DispatchStrategy dispatchStrategy) {
        logger.info("[INFO] AlertingService: \u001B[1mDispatching to Console\u001B[0m");
        logger.warn("[WARN] Alert: \u001B[1m`" + dispatchStrategy.getMessage() + "`\u001B[0m"); // Example, replace with actual message
    }

    private void logEmailDispatch() {
        logger.info("[INFO] AlertingService: \u001B[1mDispatching an Email\u001B[0m");
    }
}
