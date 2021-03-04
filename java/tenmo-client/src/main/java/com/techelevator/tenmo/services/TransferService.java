package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class TransferService {

    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public TransferService(String BASE_URL, AuthenticatedUser currentUser){
        this.BASE_URL=BASE_URL;
        this.currentUser= currentUser;
    }

    public User[] getUsers(){
        User[] userList = null;
        userList= restTemplate.exchange(BASE_URL + "/users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
        return userList;
    }

    private HttpEntity makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
