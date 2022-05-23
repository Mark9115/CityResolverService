package com.mark.controllers;

import com.mark.entities.PhoneNumber;
import com.mark.services.CityResolver;
import com.mark.entities.City;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {
    private final CityResolver cityResolver;

    public CityController(CityResolver cityResolver) {
        this.cityResolver = cityResolver;
    }

    @PostMapping(value = "/city", produces = {MediaType.APPLICATION_JSON_VALUE})
    public City cityHandler(@RequestBody PhoneNumber phoneNumber){
        return cityResolver.getCity(phoneNumber);
    }

    @PostMapping(value = "/citytest", produces = {MediaType.APPLICATION_JSON_VALUE})
    public City cityHandlerTest(@RequestBody PhoneNumber phoneNumber){
        City city = new City();
        city.setRegion("Москва");
        return city;
    }


}
