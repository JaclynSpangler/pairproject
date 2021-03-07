package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.Accounts;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransferService {

    private String BASE_URL;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;
    private int selectedId;
    public Scanner scanner = new Scanner(System.in);
    public User[] users;
    public BigDecimal amountToSend;

    public TransferService(String url, AuthenticatedUser currentUser)
    {
        this.BASE_URL = url;
        this.currentUser = currentUser;
    }

    public void sendMoney()
    {
        Transfer transfer = new Transfer();
        Accounts accounts = new Accounts();
        AccountService accountService = new AccountService(BASE_URL, currentUser);

        users = restTemplate.exchange(BASE_URL + "/users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();

        System.out.println("-------------------------------------------\r\n" +
                "Users\r\n" +
                "ID          Name\r\n" +
                "-------------------------------------------");

        for (User user : users)
        {
            if (user.getId() != currentUser.getUser().getId())
            {
                System.out.println(user.getId() + "          " + user.getUsername());
            }
        }
        System.out.println("---------");
        System.out.print("Enter ID of user you are sending to (0 to cancel): ");

        String selectedUser = scanner.nextLine();
        selectedId = Integer.parseInt(selectedUser);

        if (selectedId == 0)
        {
            return;
        }

        if (currentUser.getUser().getId() == selectedId)
        {
            System.out.println("You selected yourself, please try again.");
        }

        else
        {
            System.out.print("Enter Amount: ");
            String inputMoney = scanner.nextLine();
            amountToSend =  new BigDecimal(inputMoney);

            transfer.setAccountFrom(currentUser.getUser().getId());
            transfer.setAccountTo(selectedId);

            BigDecimal balance = new BigDecimal(0.00);
            balance = accountService.getAccountBalanceRequest();
            accounts.setBalance(balance);

            if (accounts.getBalance().subtract(amountToSend).compareTo(BigDecimal.ZERO)> 0) {
                createTransfer(currentUser.getUser().getId(), selectedId, amountToSend);

                System.out.println("Transfer complete");
                System.out.println();
            }

            else
            {
                System.out.println("Sorry you don't have enough funds.");
                System.out.println();
            }
        }
    }

    public String createTransfer(int accountFrom, int accountTo, BigDecimal amount)
    {
        Transfer transfer = new Transfer(accountFrom, accountTo, amount);

        transfer.setAmount(amountToSend);

        String output = restTemplate
                .exchange(BASE_URL + "/transfers", HttpMethod.POST, makeTransferEntity(transfer), String.class)
                .getBody();

        return output;
    }

    public Transfer[] transfersList()
    {
        Transfer[] transferList = null;
        try
        {
            transferList = restTemplate.exchange(BASE_URL + "account/transfers/" + currentUser.getUser().getId(),
                    HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
            System.out.println();
            System.out.println("-------------------------------------------");
            System.out.println("Transfers");
            System.out.println("ID          Sent/Received          Amount");
            System.out.println("-------------------------------------------");

            String sendReceive = "";

            for (Transfer transfer : transferList)
            {
                if (currentUser.getUser().getId() == transfer.getAccountFrom())
                {
                    sendReceive = "Sent";
                } else
                {
                    sendReceive = "Received";
                }
                System.out.println(
                        transfer.getTransferId() + "          " + sendReceive + "          $" + transfer.getAmount());
            }
            System.out.println("-------------------------------------------");
            System.out.println();
            //System.out.println("Please enter transfer ID to view details (0 to cancel): ");
            // not complete yet


        } catch (Exception e)
        {
            System.out.println("Error with transaction. Please contact Gregor at 1-800-Help-Me-Now ");
        }
        return transferList;
    }

    private HttpEntity makeAuthEntity()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }


//
//    public User[] getUsers(){
//        User[] userList = null;
//        userList= restTemplate.exchange(BASE_URL + "/users", HttpMethod.GET, makeAuthEntity(), User[].class).getBody();
//        return userList;
//    }
//
//    private HttpEntity makeAuthEntity(){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(currentUser.getToken());
//        HttpEntity entity = new HttpEntity<>(headers);
//        return entity;
//    }
}
