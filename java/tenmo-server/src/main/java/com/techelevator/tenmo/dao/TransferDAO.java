package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDAO
{
    List<User> getUsers();

    String createTransfer(int accountFrom, int accountTo, BigDecimal amount);

    List<Transfer> getAllTransfers(int id);



}
