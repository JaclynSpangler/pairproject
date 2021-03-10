package com.techelevator.tenmo.dao;


import java.util.List;

import com.techelevator.tenmo.model.Account;

public interface AccountDAO {

    Account getAccountByUserId(int userId);

    void updateBalance(Account account);
}