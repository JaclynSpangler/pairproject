package com.techelevator.VendingMachineParts;

import com.techelevator.view.MenuDrivenCLI;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class Sales {

    private static final BigDecimal STARTING_BALANCE = BigDecimal.ZERO;
    private BigDecimal customerBalance = STARTING_BALANCE; //change balance and whats being deposited



    public BigDecimal getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(BigDecimal customerBalance) {
        this.customerBalance = customerBalance;
    }


    public String feedMoney(String amount) {
        String result = "";

        if (amount.contains(".")) {
            result = "Invalid Entry, Please enter a WHOLE DOLLAR amount.";
        } else {
            try {
                BigDecimal inputDollar = new BigDecimal(amount);
                setCustomerBalance(customerBalance.add(inputDollar));
            } catch (NumberFormatException e) {
                result = "Invalid Entry, Please enter a WHOLE DOLLAR amount.";
            }
        }
        return result;
    }
    public String displayBalance(){
        String balanceDisplay = "";
        balanceDisplay = "$" + getCustomerBalance();
        return balanceDisplay;
    }
}
