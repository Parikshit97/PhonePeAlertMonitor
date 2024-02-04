package com.example.demo.interfaces;

import com.example.demo.alertConfigs.AlertConfigItem;

public interface ProcessAlertEvents {
    void enqueueAlertConfig(AlertConfigItem alertConfigItem);

    void processAlertConfigs();
}
