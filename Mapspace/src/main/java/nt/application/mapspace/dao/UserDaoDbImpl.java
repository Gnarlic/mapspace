/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nt.application.mapspace.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nt.application.mapspace.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Elnic
 */
public class UserDaoDbImpl implements UserDao {

    private JdbcTemplate jdbc;
    
    public void setJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    
    private static final String SQL_INSERT_USER
            = "insert into users (username, password, enabled) values (?, ?, 1)";
    private static final String SQL_INSERT_AUTHORITY
            = "insert into authorities (username, authority) values (?, ?)";
    private static final String SQL_DELETE_USER
            = "delete from users where username = ?";
    private static final String SQL_DELETE_AUTHORITIES
            = "delete from authorities where username = ?";
    private static final String SQL_GET_ALL_USERS
            = "select * from users";
    private static final String SQL_GET_USER
            = "select * from users where username = ?";
    
    @Override
    public User addUser(User user) {
        jdbc.update(SQL_INSERT_USER,
                user.getUsername(),
                user.getPassword());
        user.setId(jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class));
        ArrayList<String> authorities = user.getAuthorities();
        for (String authority : authorities) {
            jdbc.update(SQL_INSERT_AUTHORITY,
                    user.getUsername(),
                    authority);
        }
        return user;
    }

    @Override
    public void deleteUser(String username) {
        jdbc.update(SQL_DELETE_AUTHORITIES, username);
        jdbc.update(SQL_DELETE_USER, username);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbc.query(SQL_GET_ALL_USERS, new UserMapper());
    }

    @Override
    public User editUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean checkUsername(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUser(String string) {
        if (string == null) {
            string = "default";
        }
        return jdbc.queryForObject(SQL_GET_USER, new UserMapper(), string);
    }

    @Override
    public void removeAuthority(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static final class UserMapper implements RowMapper<User> {

        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}
