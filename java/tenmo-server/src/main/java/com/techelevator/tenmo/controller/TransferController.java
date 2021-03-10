package com.techelevator.tenmo.controller;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Transfer;

@RequestMapping("/account")
@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

    private TransferDAO transferDAO;
    private UserDAO userDAO;
    private AccountController accountController;
    private Transfer transfer;

    public TransferController(TransferDAO transferDAO) {
        this.transferDAO = transferDAO;
    }

    @RequestMapping(path = "account/transfers", method = RequestMethod.GET)
    public void getTransfers(Principal principal) {

        long hello = accountController.getCurrentUserId(principal);
        int helloInt = (int)hello;
        transfer.setAccount_from(helloInt);

    }

    @RequestMapping(value = "account/transfers", method = RequestMethod.POST)
    public Transfer initiateNewTransfer(@RequestBody TransferDTO transferDTO, Principal principal) {
        Transfer pendingTransfer = null;
        pendingTransfer = transferDAO.initiateTransfer(transferDTO, principal);
        transferDAO.updateBalances(pendingTransfer);
        return pendingTransfer;
    }

    //updates both accounts from the transfer
    @RequestMapping(value = "account/transfers", method = RequestMethod.PUT)
    public void makeTransfer(@RequestBody Transfer transfer) {
        transferDAO.updateBalances(transfer);
    }

}