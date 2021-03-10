package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;

import java.security.Principal;
import java.util.List;

public interface TransferDAO {



    Transfer initiateTransfer(TransferDTO transferDTO, Principal principal);

    boolean updateBalances(Transfer transfer);

    void preTransfer(TransferDTO transferDTO, Principal principal);

    List<Transfer> findAll(int userId);
    int getAccountIdByUserId(int userId);
    Transfer transferDetails(int transferId);




}