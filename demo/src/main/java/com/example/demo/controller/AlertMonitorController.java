package com.example.demo.controller;

import com.example.demo.alertConfigs.AlertConfigList;
import com.example.demo.service.AlertMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AlertMonitorController {

    private AlertMonitorService alertMonitorService;

    @Autowired
    AlertMonitorController(AlertMonitorService alertMonitorService){
        this.alertMonitorService = alertMonitorService;
    }

    @PostMapping(value = "/")
    public AlertConfigList raiseAlert(@RequestBody AlertConfigList alertConfigList){
        return alertMonitorService.getAlertConfig(alertConfigList);
    }
}
