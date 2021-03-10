package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Transfer {
    private BigDecimal amount;
    public Transfer(BigDecimal amount, int account_to) {
        super();
        this.amount = amount;
        this.account_to = account_to;
    }
    private int account_to;



    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public int getAccount_to() {
        return account_to;
    }
    public void setAccount_to(int account_to) {
        this.account_to = account_to;
    }
}