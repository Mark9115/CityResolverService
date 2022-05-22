package com.mark.services;

import com.mark.entities.City;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SenderCity {
    public void sendMessage(City city){
        RestTemplate restTemplate = new RestTemplate();

        String resourceUrl = "http://localhost:8080/testService/cityresp";
        HttpEntity<City> request = new HttpEntity<>(city);
        String productCreateResponse = restTemplate.postForObject(resourceUrl, request, String.class);

        System.out.println(productCreateResponse);
    }
}
