package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")

public class UserController {
    private AccountDAO accountDAO;
    private UserDAO userDAO;

    public UserController(UserDAO userDAO, AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUserList() {
        return userDAO.findAll();
    }
}
