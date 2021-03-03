package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDAO implements AccountDAO {
    private JdbcTemplate jdbcTemplate;
    private UserDAO userDAO;

    public JdbcAccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public BigDecimal getBalanceByUserId(Long userId) {

        User user = new User();
        String sql = "SELECT balance " +
                "FROM accounts " +
                "WHERE user_id = ?";

        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);

    }
}
