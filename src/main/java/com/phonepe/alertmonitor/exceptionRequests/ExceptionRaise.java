package com.phonepe.alertmonitor.exceptionRequests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phonepe.alertmonitor.enums.EventType;

public class ExceptionRaise {
    @JsonProperty("eventType")
    EventType eventType;
    @JsonProperty("client")
    String client;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "ExceptionRaise{" +
                "eventType=" + eventType +
                ", client='" + client + '\'' +
                '}';
    }
}
