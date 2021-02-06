package com.techelevator.VendingMachineParts;

import com.techelevator.view.MenuDrivenCLI;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class Sales {

    private static final BigDecimal STARTING_BALANCE = BigDecimal.ZERO;
    private BigDecimal customerBalance = STARTING_BALANCE; //change balance and whats being deposited
    private BigDecimal vendingMachineBalance = STARTING_BALANCE;

    public BigDecimal getVendingMachineBalance(){
        return vendingMachineBalance;
    }
    public void setVendingMachineBalance(BigDecimal vendingMachineBalance){
        this.vendingMachineBalance = vendingMachineBalance;
    }



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
        balanceDisplay = "Your current balance is: $" + getCustomerBalance();
        return balanceDisplay;
    }
//returnChange method :)


    public String returnChange(){
        String result = "";
        BigDecimal dollar = BigDecimal.ONE;
        double quarter = 0.25;
        double dime = 0.10;
        double nickel = 0.05;

        BigDecimal dollarChange = getCustomerBalance().divideToIntegralValue(dollar);
        BigDecimal coinChange = getCustomerBalance().subtract(dollarChange);
        double coinChangeDouble = coinChange.doubleValue();
        int quartersCount = 0;
        int dimesCount = 0;
        int nickelsCount = 0;

        while(coinChangeDouble>0) {
            if(coinChangeDouble >= quarter){
                coinChangeDouble -= quarter;
                quartersCount++;
            } else if(coinChangeDouble >= dime){
                coinChangeDouble -= dime;
                dimesCount++;
            } else{
                coinChangeDouble -= nickel;
                nickelsCount++;
            }
            result = "Your change is $" + dollarChange + ", " + quartersCount + " quarter(s), " + dimesCount + " dime(s), and " + nickelsCount + " nickel(s)!";
            setCustomerBalance(BigDecimal.ZERO);
        }
            return result;
        }


}
