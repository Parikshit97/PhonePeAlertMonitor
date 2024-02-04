package com.phonepe.alertmonitor.interfaces;

import com.phonepe.alertmonitor.alertConfigs.AlertConfigItem;

public interface ProcessAlertEvents {
    void enqueueAlertConfig(AlertConfigItem alertConfigItem);

    void processAlertConfigs();
}
