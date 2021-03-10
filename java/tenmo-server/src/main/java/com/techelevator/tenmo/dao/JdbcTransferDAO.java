package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    private int TRANSFER_TYPE_SEND = 2;
    private int TRANSFER_STATUS_APPROVED= 2;
    private int TRANSFER_STATUS_PENDING =1;

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
        BigDecimal stringInt =new BigDecimal(sqlBalanceString);
        if (amountToTransfer.compareTo(stringInt) == -1) {
            initiateTransfer(transferDTO, principal);
        }
        System.out.println("Not enough funds. Rejected : " + transfer.getTransfer_status_id());


    }

    @Override
    public Transfer initiateTransfer(TransferDTO transferDTO, Principal principal) {
        Transfer transfer = mapDtoToTransfer(transferDTO, principal);
        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from,account_to, amount)"
                + "VALUES (?,?,?,?,?) ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, TRANSFER_TYPE_SEND, TRANSFER_STATUS_PENDING, transfer.getAccount_from(), transfer.getAccount_to(),
                transfer.getAmount());
        while (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public boolean updateBalances(Transfer transfer) {
        boolean result = false;
        String sql = "UPDATE accounts "
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
    public List<Transfer> findAll(int userId){
        List<Transfer> transfers = new ArrayList<>();
        String sql= "SELECT t.*, u.username AS userFrom, v.username AS userTo FROM transfers t " +
                "JOIN accounts a ON t.account_from = a.account_id " +
                "JOIN accounts b ON t.account_to = b.account_id " +
                "JOIN users u ON a.user_id = u.user_id " +
                "JOIN users v ON b.user_id = v.user_id " +
                "WHERE a.user_id = ? OR b.user_id = ?";




        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while(results.next()){
            Transfer transfer= mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public Transfer transferDetails(int transferId){
        Transfer transfer = new Transfer();
        String sql= "SELECT transfers.transfer_id, transfers.account_from,transfers.account_to, "+
                "transfers.transfer_type_id, transfers.transfer_status_id, transfers.amount " +
                "FROM transfers " +
                "WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,  transferId);
        while(results.next()){
            transfer= mapRowToTransfer(results);
        }
        return  transfer;
    }
    @Override
    public int getAccountIdByUserId(int userId){

        String sql = "SELECT account_id "+
                "FROM accounts "+
                "WHERE user_id =? ";

        int accountId = jdbcTemplate.queryForObject(sql, int.class, userId);
        return accountId;


    }
    private Transfer mapDtoToTransfer(TransferDTO transferDTO, Principal principal){
        Transfer transfer= new Transfer();
        transfer.setTransfer_type_id(TRANSFER_TYPE_SEND);
        transfer.setTransfer_status_id(TRANSFER_STATUS_APPROVED);
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