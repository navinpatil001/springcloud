package com.navin.cloud.limitsservice;

import com.navin.cloud.limitsservice.bean.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public LimitConfiguration retriveLimitsConfiguration(){
        return new LimitConfiguration(configuration.getMinimum(),configuration.getMaximum());
    }

    @GetMapping("/hystrix-limits")
    @HystrixCommand(fallbackMethod = "fallbackRetriveLimitsConfigurationHystrix")
    public LimitConfiguration retriveLimitsConfigurationHystrix(){
        throw new RuntimeException("Not Available");
    }

    public LimitConfiguration fallbackRetriveLimitsConfigurationHystrix(){
        return new LimitConfiguration(9,99);
    }
}
