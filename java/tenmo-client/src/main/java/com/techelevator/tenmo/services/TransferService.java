package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Transfer;

import java.util.Scanner;

public class TransferService {


    private final String BASE_SERVICE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;


    public TransferService(String baseUrl) {
        this.BASE_SERVICE_URL = baseUrl + "account/transfers";
    }

    public Transfer createTransfer(Transfer transfer) {
        ResponseEntity<Transfer> response = restTemplate.exchange(BASE_SERVICE_URL, HttpMethod.POST, makeAuthEntity(), Transfer.class);
        return response.getBody();
    }
    public Transfer[] transfersList() {
        Transfer[] output = null;
        try {
            output = restTemplate.exchange(BASE_SERVICE_URL + "/" + currentUser.getUser().getId(), HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
            System.out.println("-------------------------------------------\r\n" +
                    "Transfers\r\n" +
                    "ID          From/To                 Amount\r\n" +
                    "-------------------------------------------\r\n");
            String fromOrTo = "";
            String name = "";
            for (Transfer transfer : output) {
                if (currentUser.getUser().getId() == transfer.getAccount_from()) {
                    fromOrTo = "From: ";
                    name = transfer.getUserTo();
                } else {
                    fromOrTo = "To: ";
                    name = transfer.getUserFrom();
                }
                System.out.println(transfer.getTransferId() + " " + fromOrTo + name + " $" + transfer.getAmount());
            }
            System.out.print("-------------------------------------------\r\n" +
                    "Please enter transfer ID to view details (0 to cancel): ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (Integer.parseInt(input) != 0) {
                boolean foundTransferId = false;
                for (Transfer transfer : output) {
                    if (Integer.parseInt(input) == transfer.getTransferId()) {
                        Transfer temp = restTemplate.exchange(BASE_SERVICE_URL + "transfers/" + transfer.getTransferId(), HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
                        foundTransferId = true;
                        System.out.println("--------------------------------------------" +
                                "Transfer Details" +
                                "--------------------------------------------" +
                                " Id: " + temp.getTransferId() + " " +
                                " From: " + temp.getUserFrom() + " " +
                                " To: " + temp.getUserTo() + " " +
                                " Type: " + temp.getTransferType() + " " +
                                " Status: " + temp.getTransferStatus() + " " +
                                " Amount: $" + temp.getAmount());
                    }
                }
            }

        } catch (Exception e) {
          System.out.println("Error :(");
        }
        return output;
    }

    public Transfer getUsers() {
        HttpEntity<?> entity = new HttpEntity<>(makeAuthEntity());
        ResponseEntity<Transfer> response = restTemplate.exchange(BASE_SERVICE_URL, HttpMethod.GET, entity, Transfer.class);
        return response.getBody();
    }
    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        return entity;
    }
}