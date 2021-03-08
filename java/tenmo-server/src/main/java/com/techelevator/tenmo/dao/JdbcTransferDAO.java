package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDAO{


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private UserDAO userDAO;


    public JdbcTransferDAO(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getUsers()
    {
        List<User> users = new ArrayList<User>();
        String sql = "SELECT user_id" +
                "        ,username" +
                "FROM users;";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql);

        while (rows.next())
        {
            User user = mapRowToUser(rows);
            users.add(user);
        }

        return users;
    }

    @Override
    public String createTransfer(int accountFrom, int accountTo, BigDecimal amount)
    {
        String sql = "INSERT INTO transfers" +
                "(" +
                "        transfer_type_id" +
                "        , transfer_status_id" +
                "        , account_from" +
                "        , account_to" +
                "        , amount" +
                ")" +
                "VALUES" +
                "(" +
                "        2" +
                "        , 2" +
                "        , ?" +
                "        , ?" +
                "        , ?" +
                ");";

        jdbcTemplate.update(sql, accountFrom, accountTo, amount);

        accountDAO.addToBalance(amount, accountTo);
        accountDAO.subtractFromBalance(amount, accountFrom);

        return "Transfer Complete!!!";
    }

    @Override
    public List<Transfer> getAllTransfers(int userId)
    {
        List<Transfer> allTransfers = new ArrayList<>();

        String sql = "SELECT t.*" +
                "        , u.username AS user_from" +
                "        , us.username AS user_to" +
                "FROM transfers AS t" +
                "INNER JOIN accounts AS a" +
                "        ON t.account_from = a.account_id" +
                "INNER JOIN accounts AS ac" +
                "        ON t.account_to = ac.account_id" +
                "INNER JOIN users AS u" +
                "        ON a.user_id = u.user_id" +
                "INNER JOIN users AS us" +
                "        ON ac.user_id = us.user_id" +
                "WHERE a.user_id = ?" +
                "        OR ac.user_id = ?;";

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