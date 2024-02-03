package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlertConfig {
    private AlertConfigEventType type;
    private int count;
    @JsonProperty("windowSizeInSecs")
    private int windowSizeInSecs;

    // Getters and setters

    @JsonProperty("type")
    public AlertConfigEventType getType() {
        return type;
    }

    public void setType(AlertConfigEventType type) {
        this.type = type;
    }

    @JsonProperty("count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getWindowSizeInSecs() {
        return windowSizeInSecs;
    }

    public void setWindowSizeInSecs(int windowSizeInSecs) {
        this.windowSizeInSecs = windowSizeInSecs;
    }

    @Override
    public String toString() {
        return "AlertConfig{" +
                "type='" + type + '\'' +
                ", count=" + count +
                ", windowSizeInSecs=" + windowSizeInSecs +
                '}';
    }
}

