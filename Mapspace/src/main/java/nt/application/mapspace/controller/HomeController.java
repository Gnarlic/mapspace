/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nt.application.mapspace.controller;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import nt.application.mapspace.dao.LocationDao;
import nt.application.mapspace.dao.LocationDaoDbImpl;
import nt.application.mapspace.dao.UserDao;
import nt.application.mapspace.model.Location;
import nt.application.mapspace.model.User;
import nt.application.mapspace.service.EmailService;
import nt.application.mapspace.service.EmailServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Elnic
 */
@Controller
public class HomeController {

    @Inject
    LocationDao lDao;
    @Inject
    UserDao uDao;
    private EmailService eSvc = new EmailServiceImpl();

    //Initial page load -- adds Location object for spring form
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("location", new Location());
        return "index";
    }

    //endpoint for ajax call for locations in db
    @RequestMapping(value = "/loadmap", method = RequestMethod.GET)
    @ResponseBody
    public List<Location> location(HttpServletRequest rq) {
        User user = uDao.getUser(rq.getParameter("user"));
        if (user == null) {
            user.setId(uDao.getUser("default").getId());
        }
        return lDao.loadLocations(user.getId());
    }

    //load user creation page
    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "/user/createUser";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String insertUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", new User());
            return "/user/createUser";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPw = encoder.encode(user.getPassword());
            user.setPassword(hashedPw);
            ArrayList<String> authorities = new ArrayList<>();
            authorities.add("ROLE_USER");
            user.setAuthorities(authorities);
            uDao.addUser(user);
            model.addAttribute("location", new Location());
            eSvc.userRegistrationSuccessEmail(user);
            return "index";
        }
    }

}
