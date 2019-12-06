/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nt.application.mapspace.dao;

import java.sql.SQLException;
import java.util.List;
import nt.application.mapspace.model.Location;

/**
 *
 * @author Elnic
 */
public interface LocationDao {

    public Location loadLocation(int locationId);
    
    public List<Location> loadLocations(int id);

    public Location addLocation(Location location, int id);
    
    public void deleteLocation(Location location);
    
}
