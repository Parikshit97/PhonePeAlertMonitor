package com.phonepe.alertmonitor.entities;

import com.phonepe.alertmonitor.alertConfigs.AlertConfig;
import com.phonepe.alertmonitor.dispatchConfigs.DispatchStrategy;
import com.phonepe.alertmonitor.enums.EventType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "clientConfigurations")
public class ClientConfiguration {

    @Id
    private String id;
    private String client;
    private EventType eventType;
    private AlertConfig alertConfig;
    private List<DispatchStrategy> dispatchStrategyList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public AlertConfig getAlertConfig() {
        return alertConfig;
    }

    public void setAlertConfig(AlertConfig alertConfig) {
        this.alertConfig = alertConfig;
    }

    public List<DispatchStrategy> getDispatchStrategyList() {
        return dispatchStrategyList;
    }

    public void setDispatchStrategyList(List<DispatchStrategy> dispatchStrategyList) {
        this.dispatchStrategyList = dispatchStrategyList;
    }
}

