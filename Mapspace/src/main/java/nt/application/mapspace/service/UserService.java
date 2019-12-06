/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nt.application.mapspace.service;

import java.util.List;
import nt.application.mapspace.model.User;

/**
 *
 * @author Elnic
 */
public interface UserService {

    public User addUser(User newUser);

    public void deleteUser(User user);

    public List<User> getAllUser();

    public User getUser(String string);

    public void editUser(User user);

    public void removeAuthority(User user);

}
