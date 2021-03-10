package com.techelevator.tenmo.services;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;

public class TransferService {


    private final String BASE_SERVICE_URL;
    private RestTemplate restTemplate = new RestTemplate();


    public TransferService(String baseUrl) {
        this.BASE_SERVICE_URL = baseUrl + "account/transfers";
    }

    public Transfer createTransfer(Transfer transfer, String authToken) {
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, authHeaders(authToken));
        ResponseEntity<Transfer> response = restTemplate.exchange(BASE_SERVICE_URL, HttpMethod.POST, entity, Transfer.class);
        return response.getBody();
    }


    public Transfer getUsers(String authToken) {
        HttpEntity<?> entity = new HttpEntity<>(authHeaders(authToken));
        ResponseEntity<Transfer> response = restTemplate.exchange(BASE_SERVICE_URL, HttpMethod.GET, entity, Transfer.class);
        return response.getBody();
    }
    private HttpHeaders authHeaders(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return headers;
    }
}