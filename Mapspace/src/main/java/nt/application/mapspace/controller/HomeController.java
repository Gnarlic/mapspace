/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nt.application.mapspace.controller;

import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import nt.application.mapspace.dao.LocationDao;
import nt.application.mapspace.dao.LocationDaoDbImpl;
import nt.application.mapspace.dao.UserDao;
import nt.application.mapspace.model.Location;
import nt.application.mapspace.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    
    //Initial page load -- adds Location object for spring form
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String homePage(Model model) {
        model.addAttribute("location", new Location());
        return "index";
    }
    
    //endpoint for ajax call for locations in db
    @RequestMapping(value="/loadmap", method=RequestMethod.GET)
    @ResponseBody
    public List<Location> location(HttpServletRequest rq) {
        User user = uDao.getUser(rq.getParameter("user"));
        if (user == null) {
            user.setId(uDao.getUser("default").getId());
        }
        return lDao.loadLocations(user.getId());
    }
    
    
    
}
