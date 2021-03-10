package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private int transferTypeId;
    private int transferStatusId;
    private int account_from;
    private int account_to;
    private BigDecimal amount;
    private String transferType;
    private String transferStatus;
    private int userFrom;
    private int userTo;

    public int getUserFrom() {
        return userFrom;
    }
    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }
    public int getUserTo() {
        return userTo;
    }
    public void setUserTo(int userTo) {
        this.userTo = userTo;
    }
    public String getTransferType() {
        return transferType;
    }
    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }
    public String getTransferStatus() {
        return transferStatus;
    }
    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }
    public int getTransferId() {
        return transferId;
    }
    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }
    public int getTransferTypeId() {
        return transferTypeId;
    }
    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }
    public int getTransferStatusId() {
        return transferStatusId;
    }
    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }
    public int getAccount_from() {
        return account_from;
    }
    public void setAccount_from(int account_From) {
        this.account_from = account_From;
    }
    public int getAccount_to() {
        return account_to;
    }
    public void setAccount_to(int accountTo) {
        this.account_to = account_to;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}