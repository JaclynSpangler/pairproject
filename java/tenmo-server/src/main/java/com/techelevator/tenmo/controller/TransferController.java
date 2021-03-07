package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.JdbcTransferDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transfers")
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDAO transferDAO;
    private AccountDAO accountDAO;
    private UserDAO userDAO;

    public TransferController(TransferDAO transferDAO, AccountDAO accountDAO, UserDAO userDAO){
        this.transferDAO=transferDAO;
        this.accountDAO = accountDAO;
        this.userDAO= userDAO;
    }

//    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
//    public Transfer getTransfer(@PathVariable Long id, Principal principal){
//        Transfer transfer = transferDAO.getTransferById(id);
//       // validateAuthorizationToView(principal, transfer);
//        return transfer;
//    }
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @RequestMapping(path= "", method = RequestMethod.POST)
//    public Transfer createTransfer(@Valid @RequestBody Transfer transfer, Principal principal){
//        return transferDAO.addTransfer(transfer);
//
//    }


    @ResponseStatus (HttpStatus.CREATED)
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public String createTransfer(@RequestBody Transfer transfer)
    {
        int accountFrom =transfer.getAccountFrom();
        int accountTo= transfer.getAccountTo();
        BigDecimal amount = new BigDecimal(String.valueOf(transfer.getAmount()));
        String results;
        results = JdbcTransferDAO.createTransfer(accountFrom, accountTo, amount);

        return results;
    }

    @RequestMapping(value = "account/transfers/{id}", method = RequestMethod.GET)
    public List<Transfer> getAllMyTransfers(@PathVariable int id)
    {
        List<Transfer> transfers = transferDAO.getAllTransfers(id);

        return transfers;
    }


}
