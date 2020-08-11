/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nt.application.mapspace.service;

import nt.application.mapspace.model.User;

/**
 *
 * @author Elnic
 */
public interface EmailService {
    
    public void userRegistrationSuccessEmail(User user);
    
}
