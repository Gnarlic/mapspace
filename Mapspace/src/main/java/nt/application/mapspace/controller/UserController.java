/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nt.application.mapspace.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import nt.application.mapspace.dao.UserDao;
import nt.application.mapspace.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Elnic
 */
@Controller
public class UserController {
    
    private UserDao uDao;
    private PasswordEncoder pwenc;
    
    @Inject
    public UserController(UserDao uDao, PasswordEncoder pwenc) {
        this.uDao = uDao;
        this.pwenc = pwenc;
    }
    
    @RequestMapping(value="/addUser", method = RequestMethod.POST)
    public String addUser(HttpServletRequest req) {
        User newUser = new User();
        newUser.setUsername(req.getParameter("username"));
        newUser.setPassword(req.getParameter("password"));
        return "redirect:/";
    }
    
}
