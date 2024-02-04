package com.phonepe.alertmonitor.logDispatcher;

import com.phonepe.alertmonitor.dispatchConfigs.DispatchStrategy;
import com.phonepe.alertmonitor.entities.ClientConfiguration;
import com.phonepe.alertmonitor.entities.GlobalCounter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LogDispatcher {

    /**
     *  LogDispatcher : provides utility functions to generate logs.
     */

    private static final Logger logger = LogManager.getLogger(LogDispatcher.class);

    public void logStart(ClientConfiguration clientConfiguration) {
        logger.info("[INFO] MonitoringService: Client {} {} {} starts", clientConfiguration.getClient(),
                clientConfiguration.getEventType(),
                clientConfiguration.getAlertConfig().getType());
    }

    public void logEnd(ClientConfiguration clientConfiguration) {
        logger.info("[INFO] MonitoringService: Client {} {} {} ends", clientConfiguration.getClient(),
                clientConfiguration.getEventType(),
                clientConfiguration.getAlertConfig().getType());
    }

    public void logConsoleDispatch(DispatchStrategy dispatchStrategy) {
        logger.info("[INFO] AlertingService: \u001B[1mDispatching to Console\u001B[0m");
        logger.warn("[WARN] Alert: \u001B[1m`" + dispatchStrategy.getMessage() + "`\u001B[0m");
    }

    public void logEmailDispatch() {
        logger.info("[INFO] AlertingService: \u001B[1mDispatching an Email\u001B[0m");
    }

    public void logThreshold(GlobalCounter globalCounter) {
        logger.info("[INFO] MonitoringService: Client {} {} \u001B[1mthreshold breached\u001B[0m",
                globalCounter.getClient(),
                globalCounter.getEventType());
    }

}
