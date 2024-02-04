package com.phonepe.alertmonitor.entities;

import com.phonepe.alertmonitor.enums.EventType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "globalCounters")
public class GlobalCounter {

    @Id
    private String id;
    private String client;
    private EventType eventType;
    private Long count;
    private Long windowSizeInSecs;
    private Long gc;
    private Long timeNow;
    private Long timeUpdated;

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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getWindowSizeInSecs() {
        return windowSizeInSecs;
    }

    public void setWindowSizeInSecs(Long windowSizeInSecs) {
        this.windowSizeInSecs = windowSizeInSecs;
    }

    public Long getGc() {
        return gc;
    }

    public void setGc(Long gc) {
        this.gc = gc;
    }

    public Long getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(Long timeNow) {
        this.timeNow = timeNow;
    }

    public Long getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(Long timeUpdated) {
        this.timeUpdated = timeUpdated;
    }
}

