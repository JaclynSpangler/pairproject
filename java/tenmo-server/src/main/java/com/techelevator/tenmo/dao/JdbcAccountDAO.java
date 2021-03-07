package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDAO implements AccountDAO {
    private JdbcTemplate jdbcTemplate;
    private UserDAO userDAO;
    private User user;
    private BigDecimal balance;

    public JdbcAccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public User getUser()
    {
        return user;
    }

    @Override
    public BigDecimal getAccountBalance(int id) {
        return null;
    }

    public BigDecimal getBalance()
    {
        return balance;
    }


    @Override
    public BigDecimal getBalanceByUserId(Long userId) {

        User user = new User();
        String sql = "SELECT balance " +
                "FROM accounts " +
                "WHERE user_id = ?";

        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);

    }
    @Override
    public Account getAccountByUserId(Long userId){
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while(results.next()){
            account = mapRowToAccount(results);
        }
        return account;
    }


    @Override
    public BigDecimal addToBalance(BigDecimal amountToAdd, int id)
    {
        Account account = findAccountById(id);

        BigDecimal newBalance = new BigDecimal(0.00);
        newBalance = account.getBalance().add(amountToAdd);

        String sql = "UPDATE accounts\r\n" +
                "SET balance = ?\r\n" +
                "WHERE user_id = ?;";
        try
        {
            jdbcTemplate.update(sql, newBalance, id);
        } catch (DataAccessException e)
        {
            System.out.println("Error accessing data");
        }
        return account.getBalance();
    }

    @Override
    public BigDecimal subtractFromBalance(BigDecimal amountToSubtract, int id)
    {
        Account account = findAccountById(id);

        BigDecimal newBalance = new BigDecimal(0.00);
        newBalance = account.getBalance().subtract(amountToSubtract);

        String sql = "UPDATE accounts " +
                "SET balance = ? " +
                "WHERE user_id = ?";
        try
        {
            jdbcTemplate.update(sql, newBalance, id);
        } catch (DataAccessException e)
        {
            System.out.println("Error accessing data");
        }
        return account.getBalance();
    }

    @Override
    public Account findAccountById(int id)
    {
        Account account = null;

        String sql = "SELECT * " +
                "FROM accounts " +
                "WHERE account_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);

        if (results.next())
        {
            account = mapRowToAccount(results);
        }

        return account;
    }

    private Account mapRowToAccount(SqlRowSet result)
    {
        Account account = new Account();

        account.setBalance(result.getBigDecimal("balance"));
        account.setAccountId(result.getInt("account_id"));
        account.setUserId(result.getInt("user_id"));

        return account;
    }

}
