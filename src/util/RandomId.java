/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import config.jdbcConfig;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import restaurant.manager.controllers.PhieuDatPhongController;

/**
 *
 * @author Luxury
 */
public final class RandomId {

    private static String id = null;

    public final static String createNewID(String value) {

        try {
            CallableStatement command = jdbcConfig.connection.prepareCall("{call CreateNewID}");
            ResultSet r = jdbcConfig.ExecuteQuery(command);
            while (r.next()) {
                id = value + "-" + r.getString(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
}
