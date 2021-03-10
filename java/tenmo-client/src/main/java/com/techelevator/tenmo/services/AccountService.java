package com.techelevator.tenmo.services;

import java.math.BigDecimal;

import com.techelevator.tenmo.models.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AccountService {

    private final String BASE_SERVICE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public AccountService(String baseUrl) {
        this.BASE_SERVICE_URL = baseUrl + "account/";
    }

    public BigDecimal getBalance(String authToken) {
        HttpEntity<?> entity = new HttpEntity<>(authHeaders(authToken));
        ResponseEntity<BigDecimal> response = restTemplate.exchange(BASE_SERVICE_URL + "balance", HttpMethod.GET, entity, BigDecimal.class);
        return response.getBody();
    }

    private HttpHeaders authHeaders(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return headers;
    }
}
