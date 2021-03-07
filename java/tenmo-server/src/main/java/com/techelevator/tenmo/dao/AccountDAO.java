package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;

public interface AccountDAO {

    User getUser();

    BigDecimal getAccountBalance(int id);

    BigDecimal addToBalance(BigDecimal amount, int accountTo);

    BigDecimal subtractFromBalance(BigDecimal amount, int accountFrom);

    Account findAccountById(int id);

}

