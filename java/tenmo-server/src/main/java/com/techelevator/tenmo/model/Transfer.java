package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private BigDecimal amount;
    private int transferId;
    private int transferStatusId;
    private int transferTypeId;
    private int accountFrom;
    private int accountTo;
    private String transferStatus;
    private String transferType;
    private String userNameReceive;
    private String userNameSend;

    public Transfer(){

    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getTransferId() {
        return transferId;
    }


    public int getAccountFrom() {
        return accountFrom;
    }

    public int getTransferStatusId() {
        return transferStatusId;
    }

    public int getAccountTo() {
        return accountTo;
    }

    public int getTransferTypeId() {
        return transferTypeId;
    }

    public String getTransferType() {
        return transferType;
    }

    public String getUserNameReceive() {
        return userNameReceive;
    }

    public String getUserNameSend() {
        return userNameSend;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setAccountFrom(int accountFrom) {
        this.accountFrom = accountFrom;
    }

    public void setAccountTo(int accountTo) {
        this.accountTo = accountTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setTransferStatusId(int transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public void setTransferTypeId(int transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public void setUserNameReceive(String userNameReceive) {
        this.userNameReceive = userNameReceive;
    }

    public void setUserNameSend(String userNameSend) {
        this.userNameSend = userNameSend;
    }
}

