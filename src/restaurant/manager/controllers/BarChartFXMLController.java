/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.controllers;

import com.jfoenix.controls.JFXButton;
import config.jdbcConfig;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author sky
 */
public class BarChartFXMLController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        config.jdbcConfig.Connect();
        try {
            String sql = "SELECT makhachhang,tongtien FROM hoadon";
            String sql2 = "SELECT mahoadon,tongtien FROM hoadon";    
            XYChart.Series<String,Integer> series = new XYChart.Series<>();
            XYChart.Series<String,Integer> series2 = new XYChart.Series<>();   
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);      
            ResultSet r = jdbcConfig.ExecuteQuery(p);
            while (r.next()) {
                 series.getData().add(new XYChart.Data<>(r.getString(1),r.getInt(2))); 
            }
            series.setName("Tổng Tiền Mỗi Khách Hàng");
            PreparedStatement p2 = jdbcConfig.connection.prepareStatement(sql2);
            ResultSet r2 = jdbcConfig.ExecuteQuery(p2);
            while (r2.next()) {
                 series2.getData().add(new XYChart.Data<>(r2.getString(1),r2.getInt(2))); 
            }
            series2.setName("Tổng Tiền Mỗi Hóa Đơn");
            barChart.getData().addAll(series,series2);   
            barChart.setTitle("Bảng Thống Kê Tiền Hóa Đơn");
        } catch (Exception e) {
        }
        
        
    }    
    
}
