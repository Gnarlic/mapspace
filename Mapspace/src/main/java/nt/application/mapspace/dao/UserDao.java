/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nt.application.mapspace.dao;

import java.util.List;
import nt.application.mapspace.model.User;

/**
 *
 * @author Elnic
 */
public interface UserDao {
    
    public User addUser(User user);
    
    public void deleteUser(String username);
    
    public List<User> getAllUsers();
    
    public User editUser(User user);
    
    public Boolean checkUsername(String username);
    
    public User getUser(String string);
    
    public void removeAuthority(User user);
    
}
