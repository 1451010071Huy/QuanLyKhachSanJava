package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Luxury
 */
public class jdbcConfig {

    public static final String URL = "jdbc:sqlserver://localhost;databaseName=QLKS;integratedSecurity=true;";
    public static Connection connection;

    /**
     * Connect dùng để kết nối Database
     */
    public static boolean Connect() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(URL);
            if (connection != null) {
                System.out.println("Connected");
                //  connection.close();
            }
            return true;
        } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Connected fail");
            return false;
        }
    }

    /**
     * Disconnect dùng để ngắt kết nối
     */
    public static void Disconnect() throws SQLException {
        connection.close();
        System.out.println("Disconnection");
    }

    /**
     * ExecuteQuery để thực thi câu truy vấn
     *
     * @param sql PreparedStatement (câu truy vấn)
     * @return
     */
    public static ResultSet ExecuteQuery(PreparedStatement sql) {
        try {
            return sql.executeQuery();
        } catch (SQLException e) {
            Logger.getLogger(jdbcConfig.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public static int ExecuteUpdateQuery(PreparedStatement sql) {
        try {
            return sql.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(jdbcConfig.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
}
