package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.JdbcTransferDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController
{
    @Autowired
    private TransferDAO transferDAO;
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private UserDAO userDAO;

    public TransferController(TransferDAO transferDAO, AccountDAO accountDAO, UserDAO userDAO)
    {
        this.transferDAO = transferDAO;
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsersList()
    {
        List<User> users = userDAO.findAll();

        return users;
    }

    @ResponseStatus (HttpStatus.CREATED)
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public String createTransfer(@RequestBody Transfer transfer)
    {
        String results = transferDAO.createTransfer(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());

        return results;
    }

    @RequestMapping(value = "account/transfers/{id}", method = RequestMethod.GET)
    public List<Transfer> getAllMyTransfers(@PathVariable int id)
    {
        List<Transfer> transfers = transferDAO.getAllTransfers(id);

        return transfers;
    }


}