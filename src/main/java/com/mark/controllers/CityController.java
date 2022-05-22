package com.mark.controllers;

import com.mark.services.CityResolver;
import com.mark.services.SenderCity;
import com.mark.entities.City;
import com.mark.entities.Number;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {
    private final CityResolver cityResolver;
    private final SenderCity sender;

    public CityController(CityResolver cityResolver, SenderCity sender) {
        this.cityResolver = cityResolver;
        this.sender = sender;
    }

    @PostMapping(value = "/city", produces = {MediaType.APPLICATION_JSON_VALUE})
    public City cityHandler(@RequestBody Number number){
        sender.sendMessage(cityResolver.getCity(number));
        return cityResolver.getCity(number);
    }
}
