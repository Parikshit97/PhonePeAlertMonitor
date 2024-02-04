package com.phonepe.alertmonitor.controller;

import com.phonepe.alertmonitor.alertConfigs.AlertConfigList;
import com.phonepe.alertmonitor.exceptionRequests.ExceptionRaise;
import com.phonepe.alertmonitor.service.AlertMonitorService;
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
    public void saveClientConfigurations(@RequestBody AlertConfigList alertConfigList){
        alertMonitorService.saveClientConfigurations(alertConfigList);
    }

    @PostMapping(value = "/raise")
    public void raiseException(@RequestBody ExceptionRaise exceptionRaise){
        alertMonitorService.raiseException(exceptionRaise);
    }


}
