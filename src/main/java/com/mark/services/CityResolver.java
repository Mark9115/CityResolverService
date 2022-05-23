package com.mark.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mark.entities.City;
import com.mark.entities.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@PropertySource("classpath:values.properties")
public class CityResolver {
    @Value("${properties.Token}")
    private String token;
    @Value("${properties.Secret}")
    private String secret;

    public City getCity(PhoneNumber phoneNumber) {
        City city = new City();
        RestTemplate restTemplate = new RestTemplate();

        String resourceUrl = "https://cleaner.dadata.ru/api/v1/clean/phone";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        headers.set("X-Secret", secret);

        String number = phoneNumber.getNumber();
        ResponseEntity<String> responseEntity = ResponseEntity.ok().headers(headers).body("[\"" + number + "\"]");
        ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl, responseEntity, String.class);

        String needToParseCity;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<JsonNode> myObjects = Arrays.asList(objectMapper.readValue(response.getBody(), JsonNode[].class));
            needToParseCity = myObjects.get(0).at("/region").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        int indexOfBreakSpace = needToParseCity.trim().indexOf(" ");
        if (indexOfBreakSpace > 0) {
            String parsedCity = needToParseCity.substring(0, indexOfBreakSpace);
            city.setRegion(parsedCity);
        } else {
            city.setRegion(needToParseCity);
        }

        return city;
    }
}
