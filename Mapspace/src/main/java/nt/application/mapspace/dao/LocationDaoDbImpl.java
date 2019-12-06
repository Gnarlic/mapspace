/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nt.application.mapspace.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import nt.application.mapspace.model.Location;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Elnic
 */
public class LocationDaoDbImpl implements LocationDao {

    private static final String SQL_GET_LOCATIONS = "select l.location_id, l.location_name, l.location_description, l.location_latitude, l.location_longitude\n"
            + "from users u\n"
            + "inner join users_location ul on u.user_id  = ul.user_id\n"
            + "inner join locations l on ul.location_id = l.location_id\n"
            + "where u.user_id = ?";
    private static final String SQL_ADD_LOCATION = "INSERT INTO locations (location_name, location_latitude, location_longitude) VALUES (?, ?, ?)";
    private static final String SQL_GET_SINGLE_LOCATION = "select * from locations where location_id = ?";
    private static final String SQL_DELETE_LOCATION = "delete from locations where location_id = ?";
    private static final String SQL_ADD_USER_LOCATION = "insert into users_location (user_id, location_id) value (?,?)";

    private JdbcTemplate jdbc;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    @Override
    public List<Location> loadLocations(int id) {

        return jdbc.query(SQL_GET_LOCATIONS, new LocationMapper(), id);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Location addLocation(Location location, int id) {

        jdbc.update(SQL_ADD_LOCATION, location.getLocationName(), location.getLat(), location.getLon());
        int locationId = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        location.setId(locationId);
        jdbc.update(SQL_ADD_USER_LOCATION, id, location.getId());
        return location;

    }

    @Override
    public Location loadLocation(int id) {
        return jdbc.queryForObject(SQL_GET_SINGLE_LOCATION, new LocationMapper(), id);
    }

    @Override
    public void deleteLocation(Location location) {
        jdbc.update(SQL_DELETE_LOCATION, location.getId());
    }

    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {
            Location loc = new Location();
            loc.setId(rs.getInt("location_id"));
            loc.setLocationName(rs.getString("location_name"));
            loc.setLat(Double.parseDouble(rs.getString("location_latitude")));
            loc.setLon(Double.parseDouble(rs.getString("location_longitude")));
            return loc;
        }
    }

}
