package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
     *
     * @return
     */
    public static boolean Connect() {
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
    public static void Disconnect() {
        try {
            connection.close();
            System.out.println("Disconnection");
        } catch (SQLException ex) {
            Logger.getLogger(jdbcConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    /**
     * setTableV1
     *
     * @param tab tableView mà bạn muốn đổ dữ liệu vào
     * @param listCols danh sách các cột
     * @param listName tên các trường của model
     * @param obList danh sách ObservableList của bạn
     * @see Ví dụ : TableColumn[] tabView =
     * {tblColMaPhieuDatDS,tblColKhachHangDS}; String[] listName =
     * {"maPhieuDat", "maKhachHang"}; this.setTableV1(tblPhieuDatDS, tabView,
     * listName, getPhieuDatPhong());
     * @since 1.0
     */
    public static void setTableV1(TableView tab, TableColumn[] listCols,
            String[] listName, ObservableList obList) {
        tab.setItems(obList);
        for (int i = 0; i < listCols.length; i++) {
            listCols[i].setCellValueFactory(new PropertyValueFactory<>(listName[i]));
        }

    }

    /**
     * Hàm này dùng để đổ dữ liệu vào TableView
     *
     * @param table Tên TableView
     * @param mapCol Biến này là 1 dictionary Map TableColums với tên trường
     * model
     * @param obList Cái này là biến ObservableList để lưu
     * @see Ví dụ: Map<TableColumn,String> mapCol = new HashMap<>();
     * mapCol.put(tblColMaPhieuDatDS, "maPhieuDat");
     * mapCol.put(tblColKhachHangDS, "maKhachHang"); "ngayDen");
     * this.setTableV2(tblPhieuDatDS, mapCol, getPhieuDatPhong());
     * @since 2.0
     */
    public static void setTableView(TableView table, Map<TableColumn, String> mapCol, ObservableList obList) {
        table.setItems(obList);
        mapCol.entrySet().forEach((map) -> {
            map.getKey().setCellValueFactory(new PropertyValueFactory<>(map.getValue()));
        });
    }

}
