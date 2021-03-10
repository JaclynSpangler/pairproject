package com.techelevator.tenmo.model;


import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class TransferDTO {

    @NotNull
    private int account_from;
    @NotNull
    private int account_to;
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal amount;
    @NotNull
    private int transfer_type_id;

    public TransferDTO(@NotNull int account_from, @NotNull int account_to, @NotNull BigDecimal amount,
                       @NotNull int transfer_type_id) {
        this.account_from = account_from;
        this.account_to = account_to;
        this.amount = amount;
        this.transfer_type_id = transfer_type_id;
    }

    public int getAccount_from() {
        return account_from;
    }

    public int getAccount_to() {
        return account_to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getTransfer_type_id() {
        return transfer_type_id;
    }

    public void setAccount_from(int account_from) {
        this.account_from = account_from;
    }

    public void setAccount_to(int account_to) {
        this.account_to = account_to;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransfer_type_id(int transfer_type_id) {
        this.transfer_type_id = transfer_type_id;
    }

}