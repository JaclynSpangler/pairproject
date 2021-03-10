package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;

import java.security.Principal;

public interface TransferDAO {



    Transfer initiateTransfer(TransferDTO transferDTO, Principal principal);

    boolean updateBalances(Transfer transfer);

    void preTransfer(TransferDTO transferDTO, Principal principal);
    int getAccountIdByUserId(int userId);
    Transfer mapDtoToTransfer(TransferDTO transferDTO, Principal principal);




}