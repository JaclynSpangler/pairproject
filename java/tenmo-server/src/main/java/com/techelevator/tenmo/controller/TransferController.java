package com.techelevator.tenmo.controller;


import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

//    @RequestMapping(path = "/transfers", method = RequestMethod.GET)
//    public void getTransfers(Principal principal) {
//
//        int userId = accountController.getCurrentUserId(principal);
//
//        transfer.setAccount_from(userId);
//
//    }

    @RequestMapping(path= "/transfers/{transferId}/details", method = RequestMethod.GET)
    public Transfer getTransferDetails(@PathVariable int transferId){
        return getTransferDetails(transferId);
    }

    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public Transfer initiateNewTransfer(@RequestBody TransferDTO transferDTO, Principal principal) {
        Transfer pendingTransfer = null;
        pendingTransfer = transferDAO.initiateTransfer(transferDTO, principal);
        transferDAO.updateBalances(pendingTransfer);
        return pendingTransfer;
    }

    //updates both accounts from the transfer
    @RequestMapping(path = "/transfers", method = RequestMethod.PUT)
    public void makeTransfer(@RequestBody Transfer transfer) {
        transferDAO.updateBalances(transfer);
    }

    @RequestMapping(path = "/transfers/all", method = RequestMethod.GET)
    public List<Transfer> getAllMyTransfers(Principal principal) {
        List<Transfer> output = transferDAO.findAll(userDAO.findIdByUsername(principal.getName()));
        return output;
    }



}