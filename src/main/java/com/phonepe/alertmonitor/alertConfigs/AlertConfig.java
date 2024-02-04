package com.phonepe.alertmonitor.alertConfigs;

import com.phonepe.alertmonitor.enums.AlertConfigEventType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlertConfig {
    private AlertConfigEventType type;
    private int count;
    @JsonProperty("windowSizeInSecs")
    private Long windowSizeInSecs;

    // Getters and setters
    @JsonProperty("type")
    public AlertConfigEventType getType() {
        return type;
    }

    public void setType(AlertConfigEventType type) {
        this.type = type;
    }

    @JsonProperty("count")
    public long getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getWindowSizeInSecs() {
        return windowSizeInSecs;
    }

    public void setWindowSizeInSecs(Long windowSizeInSecs) {
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

