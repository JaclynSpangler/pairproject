package com.techelevator.VendingMachineParts;

import com.techelevator.util.VMLog;

import java.math.BigDecimal;

public class Sales {

    private static final BigDecimal STARTING_BALANCE = new BigDecimal("0.00");
    private BigDecimal customerBalance = STARTING_BALANCE; //change balance and whats being deposited
    private BigDecimal vendingMachineBalance = STARTING_BALANCE;

    public BigDecimal getVendingMachineBalance(){
        return vendingMachineBalance;
    }
    public void setVendingMachineBalance(BigDecimal vendingMachineBalance){
        this.vendingMachineBalance = vendingMachineBalance;
    }

    public String getReturnChange(){ return returnChange();}

    public BigDecimal getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(BigDecimal customerBalance) {
        this.customerBalance = customerBalance;
    }


    public String feedMoney(String amount) {
        String result = "";

        if (amount.contains(".") || amount.contains("-")) {
            result = "Invalid Entry, Please enter a WHOLE DOLLAR amount.";
        } else {
            try {
                BigDecimal inputDollar = new BigDecimal(amount);
                setCustomerBalance(customerBalance.add(inputDollar));
                String feedMoneyLog = " FEED MONEY : Input Amount: $" +
                        inputDollar.toString() + " , Total Balance: $" +
                        getCustomerBalance().toString();
                VMLog.log(feedMoneyLog);
            } catch (NumberFormatException e) {
                result = "Invalid Entry, Please enter a WHOLE DOLLAR amount.";
            }
        }
        return result;
    }
    public String displayBalance(){
       return "Current Balance: $" + getCustomerBalance();
    }

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
        String transactionCompleteLog = "";

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
        }
        result = "Change Given: $" + dollarChange + ", " + quartersCount + " quarter(s), " + dimesCount + " dime(s), " + nickelsCount + " nickel(s)";
        setCustomerBalance(BigDecimal.ZERO);
        transactionCompleteLog = " GIVE CHANGE: " + result + ", Remaining Balance: $" + getCustomerBalance();
        VMLog.log(transactionCompleteLog);
            return result;
    }

}
