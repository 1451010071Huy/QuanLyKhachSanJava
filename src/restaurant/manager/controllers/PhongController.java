/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.controllers;

import config.jdbcConfig;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import restaurant.manager.models.LoaiPhong;
import restaurant.manager.models.Phong;

/**
 * FXML Controller class
 *
 * @author Luxury
 */
public class PhongController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @FXML
    private Button btnCapNhatLP;

    @FXML
    private TableColumn<Phong, Double> tblColGia1;

    @FXML
    private TextField txtSoNguoi;

    @FXML
    private TableColumn<LoaiPhong, Double> tblColGia;

    @FXML
    private TextField txtMaLoai;

    @FXML
    private TextField txtGia;

    @FXML
    private ComboBox<?> cbbLoaiPhong;

    @FXML
    private Button btnThemLP;

    @FXML
    private Button btnThemPhong;

    @FXML
    private Button btnXoaLP;

    @FXML
    private Button btnXoaPhong;

    @FXML
    private TableView<LoaiPhong> tblLoaiPhong;

    @FXML
    private TableColumn<LoaiPhong, String> tblColMaLoai;

    @FXML
    private TableColumn<LoaiPhong, Integer> tblColSoNguoi;

    @FXML
    private TableView<Phong> tblPhong;

    @FXML
    private TableColumn<Phong, Integer> tblColSoNguoi1;

    @FXML
    private TableColumn<Phong, String> tblColMaPhong;
    @FXML

    private TableColumn<Phong, String> tblColLoaiPhong1;
    @FXML
    private Button btnCapNhatphong;

    @FXML
    private TextField txtPhong;

    private ObservableList<LoaiPhong> listLoaiPhong;
    private ObservableList<Phong> listPhong;

    private ObservableList<LoaiPhong> getLoaiPhong() throws SQLException {
        jdbcConfig.Connect();// Kết nối 
        String sql = "SELECT * FROM loaiphong";
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);

        ResultSet r = jdbcConfig.ExecuteQuery(p);//Thực thi câu truy vấn
        listLoaiPhong = FXCollections.observableArrayList();
        while (r.next()) {
            listLoaiPhong.add(new LoaiPhong(r.getString(1),
                    Double.parseDouble(r.getString(2)),
                    Integer.parseInt(r.getString(3))));
        }
        jdbcConfig.Disconnect();
        return listLoaiPhong;
    }

    private void insertPhong() throws SQLException {
        jdbcConfig.Connect();
        String sql = String.format("INSERT INTO loaiphong(maloai, gia, songuoi)"
                + "VALUES (?, ?, ?)");

        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtMaLoai.getText());
        p.setString(2, txtGia.getText());
        p.setString(3, txtSoNguoi.getText());
        int rows = jdbcConfig.ExecuteUpdateQuery(p);
        if (rows != 0) {
            setTableLoaiPhong();
        }
        jdbcConfig.Disconnect();

    }

    private ObservableList<Phong> getPhong() throws SQLException {
        try {
            jdbcConfig.Connect();
            String sql = "SELECT p.maphong, lp.maloai, lp.gia, lp.songuoi \n"
                    + "FROM loaiphong as lp, phong as p\n"
                    + "where lp.maloai = p.maloai";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);

            ResultSet r = jdbcConfig.ExecuteQuery(p);//Thực thi câu truy vấn
            listPhong = FXCollections.observableArrayList();
            while (r.next()) {
                listPhong.add(new Phong(r.getString(1),
                        r.getString(2), Double.parseDouble(r.getString(3)), Integer.parseInt(r.getString(4))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            jdbcConfig.Disconnect();
        }
        return listPhong;
    }

    private void setTableLoaiPhong() throws SQLException {
        tblLoaiPhong.setItems(getLoaiPhong());//Lấy giá trị DB rồi set cho bảng 
        tblColMaLoai.setCellValueFactory(new PropertyValueFactory<>("maLoai"));//set mã loại (với mã loại là thuộc tính của model)
        tblColGia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        tblColSoNguoi.setCellValueFactory(new PropertyValueFactory<>("soNguoi"));

    }

    private void setTablePhong() throws SQLException {
        tblPhong.setItems(getPhong());//Lấy giá trị DB rồi set cho bảng   
        tblColMaPhong.setCellValueFactory(new PropertyValueFactory<>("maPhong"));//set mã loại (với mã loại là thuộc tính của model)
        tblColLoaiPhong1.setCellValueFactory(new PropertyValueFactory<>("maLoai"));
        tblColGia1.setCellValueFactory(new PropertyValueFactory<>("gia"));
        tblColSoNguoi1.setCellValueFactory(new PropertyValueFactory<>("soNguoi"));

    }

    private void deleteLoaiPhong() throws SQLException {
        jdbcConfig.Connect();
        String sql = String.format("DELETE FROM loaiphong\n"
                + "WHERE maloai = ?");
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtMaLoai.getText());
        int row = p.executeUpdate();
        if (row == 1) {
            setTableLoaiPhong();
        }
        jdbcConfig.Disconnect();
    }

    private void updateLoaiPhong() throws SQLException {
        jdbcConfig.Connect();
        String sql = String.format("update loaiphong \n"
                + "set gia = ?,\n"
                + "	songuoi = ?\n"
                + "where maloai = ? ");
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtGia.getText());
        p.setString(2, txtSoNguoi.getText());
        p.setString(3, txtMaLoai.getText());
        int row = p.executeUpdate();
        if (row == 1) {
            setTableLoaiPhong();
        }
        jdbcConfig.Disconnect();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setTableLoaiPhong();
            setTablePhong();
            btnThemLP.setOnAction(e -> {
                try {
                    insertPhong();
                } catch (SQLException ex) {
                    Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            btnXoaLP.setOnAction(e -> {
                try {
                    deleteLoaiPhong();
                } catch (SQLException ex) {
                    Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            btnCapNhatLP.setOnAction(e -> {
                try {
                    updateLoaiPhong();
                } catch (SQLException ex) {
                    Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

        } catch (SQLException ex) {
            Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
