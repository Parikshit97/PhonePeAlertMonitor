package com.example.demo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertMonitorController {
    @PostMapping(value = "/")
    public AlertConfigList raiseAlert(@RequestBody AlertConfigList alertConfigList){
        return alertConfigList;
    }
}
