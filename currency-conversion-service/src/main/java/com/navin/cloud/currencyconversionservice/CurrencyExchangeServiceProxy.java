package com.navin.cloud.currencyconversionservice;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange-service", url = "localhost:8000") //when we talk to one instance
//@FeignClient(name = "currency-exchange-service") // when communicate without zuul apigateway.
@FeignClient(name = "netflix-zuul-api-gateway-server") // routed through API gateway
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

    //@GetMapping("/currency-exchange/from/{from}/to/{to}") // when communicate without zuul apigateway and communication is betn microservices.
    @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversionBean retriveExchangeValue(@PathVariable("from") String from,
                                                       @PathVariable("to") String to);
}
