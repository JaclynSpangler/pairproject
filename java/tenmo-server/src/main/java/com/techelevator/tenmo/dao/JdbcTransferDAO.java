package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDAO{

    private JdbcTemplate jdbcTemplate;
    private AccountDAO accountDAO;
    private UserDAO userDAO;


    public JdbcTransferDAO(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getUsers()
    {
        List<User> users = new ArrayList<User>();
        String sql = "SELECT user_id " +
                "        ,username " +
                "FROM users";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);

        while (rows.next())
        {
            User user = mapRowToUser(rows);
            users.add(user);
        }

        return users;
    }

    @Override
    public String createTransfer(int accountFrom, int accountTo, BigDecimal amount) {
        String sql = "INSERT INTO transfers" +
                "(transfer_type_id, transfer_status_id, account_from, account_to, amount) "+
                "VALUES(2, 2, ?, ?, ?)";

        jdbcTemplate.update(sql, accountFrom, accountTo, amount);

        accountDAO.addToBalance(amount, accountTo);
        accountDAO.subtractFromBalance(amount, accountFrom);

        return "Transfer Complete!!!";
    }

    @Override
    public List<Transfer> getAllTransfers(int userId)
    {
        List<Transfer> allTransfers = new ArrayList<>();

        String sql = "SELECT t.*, u.username AS user_from, us.username AS user_to " +
                "FROM transfers AS t " +
                "INNER JOIN accounts AS a ON t.account_from = a.account_id " +
                "INNER JOIN accounts AS ac ON t.account_to = ac.account_id " +
                "INNER JOIN users AS u ON a.user_id = u.user_id " +
                "INNER JOIN users AS us ON ac.user_id = us.user_id " +
                "WHERE a.user_id = ? OR ac.user_id = ?";

        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userId, userId);

        while (rows.next())
        {
            Transfer transfer = mapRowToTransfer(rows);
            allTransfers.add(transfer);
        }

        return allTransfers;
    }

    private User mapRowToUser(SqlRowSet rs)
    {
        User user = new User();

        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setActivated(true);
        user.setAuthorities("ROLE_USER");

        return user;
    }

    private Transfer mapRowToTransfer(SqlRowSet rs)
    {
        Transfer transfer = new Transfer();

        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));

        return transfer;
    }

}
//@Component
//public class JdbcTransferDAO implements TransferDAO {
//    private JdbcTemplate jdbcTemplate;
//    private TransferDAO TransferDAO;
//    private AccountDAO accountDAO;
//    private UserDAO userDAO;
//
//    public JdbcTransferDAO(JdbcTemplate jdbcTemplate, AccountDAO accountDAO, UserDAO userDAO) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.accountDAO = accountDAO;
//        this.userDAO = userDAO;
//    }
//
//    private static final String SQL_SELECT_TRANSFER = "SELECT t.transfer_id, tt.transfer_type_desc, ts.transfer_status_desc, t.amount, " +
//            "aFrom.account_id as fromAcct, aFrom.user_id as fromUser, aFrom.balance as fromBal, " +
//            "aTo.account_id as toAcct, aTo.user_id as toUser, aTo.balance as toBal " +
//            "FROM transfers t " +
//            "INNER JOIN transfer_types tt ON t.transfer_type_id = tt.transfer_type_id " +
//            "INNER JOIN transfer_statuses ts ON t.transfer_status_id = ts.transfer_status_id " +
//            "INNER JOIN accounts aFrom ON account_from = aFrom.account_id " +
//            "INNER JOIN accounts aTc ON account_to = aTo.account_id ";
//
//    @Override
//    public List<Transfer> findAll() {
//        List<Transfer> transfers = new ArrayList<>();
//        SqlRowSet results = jdbcTemplate.queryForRowSet(SQL_SELECT_TRANSFER);
//        while (results.next()) {
//            Transfer transfer = mapRowToTransfer(results);
//            transfers.add(transfer);
//        }
//
//        return transfers;
//    }
//
//    @Override
//    public Transfer getTransferById(Long transferId){
//        Transfer transfer = null;
//        String sql = SQL_SELECT_TRANSFER + "WHERE transfer_id = ?";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
//        if(results.next()){
//            transfer = mapRowToTransfer(results);
//        }
//        return transfer;
//    }
//
//    @Override
//    public Transfer addTransfer(Transfer newTransfer){
//
//        String sql = "INSERT INTO transfers(transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount) "+
//                "VALUES(?, ?, ?, ?, ?, ?)";
//
//        Long id = jdbcTemplate.queryForObject(sql, Long.class, newTransfer.getTransferId(), newTransfer.getTransferTypeId(), newTransfer.getTransferStatusId(),
//                newTransfer.getAccountFrom(), newTransfer.getAccountTo(), newTransfer.getAmount());
//        newTransfer.setTransferId(id);
//        return newTransfer;
//
//    }
//
//    @Override
//    public List<Transfer> getTransfersForUser(Long userId){
//        List<Transfer> transfers = new ArrayList<>();
//        String sql = SQL_SELECT_TRANSFER +
//                " WHERE (account_from IN (SELECT account_id FROM accounts WHERE user_id =?) "+
//                "OR account_to IN (SELECT account_id FROM accounts WHERE user_id=?)";
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
//        while(results.next()){
//            Transfer transfer = mapRowToTransfer(results);
//            transfers.add(transfer);
//        }
//        return transfers;
//    }
//
//
//    private Transfer mapRowToTransfer(SqlRowSet rs) {
//        Transfer transfer = new Transfer();
//        transfer.setTransferId(rs.getLong("transfer_id"));
//        transfer.setTransferType(rs.getString("transfer_type_desc"));
//        transfer.setTransferStatus(rs.getString("transfer_status_desc"));
//        transfer.setAmount(rs.getBigDecimal("amount"));
//        transfer.setUserNameSend(rs.getString("fromUser"));
//        transfer.setUserNameReceive(rs.getString("toUser"));
//        return transfer;
//    }
//}
