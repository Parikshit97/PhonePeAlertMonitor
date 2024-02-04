package com.phonepe.alertmonitor.alertConfigs;

import com.phonepe.alertmonitor.dispatchConfigs.DispatchStrategy;
import com.phonepe.alertmonitor.enums.EventType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class AlertConfigItem {
    private String client;
    private EventType eventType;
    private AlertConfig alertConfig;
    private List<DispatchStrategy> dispatchStrategyList;

    // Getters
    @JsonProperty("client")
    public String getClient() {
        return client;
    }

    @JsonProperty("eventType")
    public EventType getEventType() {
        return eventType;
    }

    @JsonProperty("alertConfig")
    public AlertConfig getAlertConfig() {
        return alertConfig;
    }

    @JsonProperty("dispatchStrategyList")
    public List<DispatchStrategy> getDispatchStrategyList() {
        return dispatchStrategyList;
    }

    // Setters
    public void setClient(String client) {
        this.client = client;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setAlertConfig(AlertConfig alertConfig) {
        this.alertConfig = alertConfig;
    }

    public void setDispatchStrategyList(List<DispatchStrategy> dispatchStrategyList) {
        this.dispatchStrategyList = dispatchStrategyList;
    }

    @Override
    public String toString() {
        return "AlertConfigItem{" +
                "client='" + client + '\'' +
                ", eventType='" + eventType + '\'' +
                ", alertConfig=" + alertConfig +
                ", dispatchStrategyList=" + dispatchStrategyList +
                '}';
    }
}
