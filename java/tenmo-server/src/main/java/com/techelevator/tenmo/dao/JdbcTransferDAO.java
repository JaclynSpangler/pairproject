package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.security.Principal;

import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;

@Service
public class JdbcTransferDAO implements TransferDAO {

    private JdbcTemplate jdbcTemplate;
    private UserDAO userDAO;

    public JdbcTransferDAO(JdbcTemplate jdbcTemplate, UserDAO userDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDAO= userDAO;
    }

    @Override
    public void preTransfer(TransferDTO transferDTO, Principal principal) {
        Transfer transfer= mapDtoToTransfer(transferDTO, principal);
        String sqlBalanceString = "SELECT balance\r\n" +
                "FROM accounts\r\n" +
                "JOIN transfers ON accounts.account_id = transfers.account_from\r\n" +
                "WHERE accounts.account_id = 2;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlBalanceString, transfer.getAccount_from());
        BigDecimal amountToTransfer = transfer.getAmount();
        BigDecimal stringInt = BigDecimal(sqlBalanceString);
        if (amountToTransfer.compareTo(stringInt) == -1) {
            initiateTransfer(transferDTO, principal);
        }
        System.out.println("Not enough funds. Rejected : " + transfer.getTransfer_status_id());


    }


    private BigDecimal BigDecimal(String sqlBalanceString) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Transfer initiateTransfer(TransferDTO transferDTO, Principal principal) {
        Transfer transfer = mapDtoToTransfer(transferDTO, principal);
        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from,account_to, amount)"
                + "VALUES (2,1,?,?,?) RETURNING *;";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, transfer.getAccount_from(), transfer.getAccount_to(),
                transfer.getAmount());
        while (rs.next()) {
            transfer = mapRowToTransfer(rs);
        }
        return transfer;
    }

    @Override
    public boolean updateBalances(Transfer transfer) {
        boolean result = false;
        String sql = "BEGIN TRANSACTION; UPDATE accounts "
                + "SET balance = balance + (SELECT amount FROM transfers WHERE transfer_id = ? AND transfer_status_id = 1) "
                + "WHERE account_id = (SELECT account_to FROM transfers WHERE transfer_id = ? AND transfer_status_id = 1); "
                + "UPDATE accounts "
                + "SET balance = balance - (SELECT amount FROM transfers WHERE transfer_id = ? AND transfer_status_id = 1) "
                + "WHERE account_id = (SELECT account_from FROM transfers WHERE transfer_id = ? AND transfer_status_id = 1); "
                + "UPDATE transfers "
                + "SET transfer_status_id = 2"
                + " WHERE transfer_id = ?;"
                + " COMMIT;";
        int updatedCount = jdbcTemplate.update(sql, transfer.getTransfer_id(), transfer.getTransfer_id(),
                transfer.getTransfer_id(), transfer.getTransfer_id(), transfer.getTransfer_id());
        if (updatedCount == 3) {
            result = true;
        }
        return result;
    }
    @Override
    public int getAccountIdByUserId(int userId){

        String sql = "SELECT account_id "+
                "FROM accounts "+
                "WHERE user_id =? ";

        int accountId = jdbcTemplate.queryForObject(sql, int.class, userId);
        return accountId;


    }
    @Override
    public Transfer mapDtoToTransfer(TransferDTO transferDTO, Principal principal){
        Transfer transfer= new Transfer();
        transfer.setTransfer_type_id(2);
        transfer.setTransfer_status_id(2);
        transfer.setAccount_from(getAccountIdByUserId(userDAO.findIdByUsername(principal.getName())));
        transfer.setAccount_to(getAccountIdByUserId(transferDTO.getAccount_to()));
        transfer.setAmount(transferDTO.getAmount());
        return transfer;
    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransfer_id(rs.getInt("transfer_id"));
        transfer.setTransfer_type_id(rs.getInt("transfer_type_id"));
        transfer.setTransfer_status_id(rs.getInt("transfer_status_id"));
        transfer.setAccount_from(rs.getInt("account_from"));
        transfer.setAccount_to(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        return transfer;
    }

}