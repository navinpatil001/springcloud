package com.navin.cloud.currencyconversionservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    private Logger log = LoggerFactory.getLogger(CurrencyConversionController.class);

    @Autowired
    private CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from,
                                                  @PathVariable String to,
                                                  @PathVariable BigDecimal quantity){

        //Feign - Problem -1
        Map<String, String> uriVariables= new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);

        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",CurrencyConversionBean.class,
                uriVariables);

        CurrencyConversionBean conversionBean = responseEntity.getBody();

        log.info("{}"+conversionBean);

        return new CurrencyConversionBean(conversionBean.getId(),from,to,conversionBean.getConversionMultiple(),
                quantity,quantity.multiply(conversionBean.getConversionMultiple()),conversionBean.getPort());
    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,
                                                  @PathVariable String to,
                                                  @PathVariable BigDecimal quantity){

        CurrencyConversionBean conversionBean = currencyExchangeServiceProxy.retriveExchangeValue(from, to);

        log.info("{} "+conversionBean);

        return new CurrencyConversionBean(conversionBean.getId(),from,to,conversionBean.getConversionMultiple(),
                quantity,quantity.multiply(conversionBean.getConversionMultiple()),conversionBean.getPort());
    }
}
