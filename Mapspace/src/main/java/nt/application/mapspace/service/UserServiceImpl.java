/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nt.application.mapspace.service;

import java.util.List;
import javax.inject.Inject;
import nt.application.mapspace.dao.UserDao;
import nt.application.mapspace.model.User;
import org.springframework.stereotype.Service;

/**
 *
 * @author Elnic
 */

public class UserServiceImpl implements UserService {

    @Inject
    private UserDao uDao;

    @Override
    public User addUser(User newUser) {
        return uDao.addUser(newUser);
    }

    @Override
    public void deleteUser(User user) {
        uDao.deleteUser(user.getUsername());
    }

    @Override
    public List<User> getAllUser() {
        return uDao.getAllUsers();
    }

    @Override
    public User getUser(String string) {
        return uDao.getUser(string);
    }

    @Override
    public void editUser(User user) {
        uDao.editUser(user);
    }

    @Override
    public void removeAuthority(User user) {
        uDao.removeAuthority(user);
    }
    
}
