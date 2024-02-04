package com.example.demo.alertConfigs;

import com.example.demo.alertConfigs.AlertConfigItem;

import java.util.List;

public class AlertConfigList {
    private List<AlertConfigItem> alertConfigList;

    // Getters
    public List<AlertConfigItem> getAlertConfigList() {
        return alertConfigList;
    }

    // Setters
    public void setAlertConfigList(List<AlertConfigItem> alertConfigList) {
        this.alertConfigList = alertConfigList;
    }

    @Override
    public String toString() {
        return "AlertConfigList{" +
                "alertConfigList=" + alertConfigList +
                '}';
    }
}
