package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.Accounts;
import com.techelevator.tenmo.models.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;
    private BigDecimal balance;

    public AccountService(String BASE_URL, AuthenticatedUser currentUser) {
        this.BASE_URL = BASE_URL + "accounts";
        this.currentUser= currentUser;
    }

    public BigDecimal getAccountBalanceRequest()
    {
        Accounts accounts = new Accounts();

        try
        {
            BigDecimal balance = restTemplate.exchange(BASE_URL + "/" + currentUser.getUser().getId() + "/balance",
                    HttpMethod.GET, makeAuthEntity(), BigDecimal.class).getBody();

            accounts.setBalance(balance);
            return accounts.getBalance();


        } catch (RestClientResponseException ex)
        {
            System.err.println("Sorry, that didn't go as planned!");
        }
        return accounts.getBalance();
    }

    private HttpEntity makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
}
