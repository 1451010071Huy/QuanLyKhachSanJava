package restaurant.manager.controllers;





/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import config.jdbcConfig;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import restaurant.manager.models.DichVu;
import restaurant.manager.models.DonViTinh;

public class DichVuController implements Initializable {

    @FXML
    private Button btnThem;
    @FXML
    private Button btnXoa;
    @FXML
    private Button btnLuu;
    @FXML
    private TextField txtMaDichVu;
    @FXML
    private TextField txtTenDichVu;
    @FXML
    private TextField txtGia;
    @FXML
    private ComboBox<DonViTinh> CbbDonViTinh;
    private ObservableList<DichVu> listDichVu;
    @FXML
    private TableColumn<DichVu, String> tblColMaDichVu;
    @FXML
    private TableColumn<DichVu, String> tblColTenDichVu;
    @FXML
    private TableColumn<DichVu, Integer> tblColGia;
    @FXML
    private TableColumn<DichVu, String> tblColDonViTinh;
    @FXML
    private TableView<DichVu> tblDichVu;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private ObservableList<DichVu> getDichVu() throws SQLException {
        String sql = "SELECT * FROM dichvu";
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        ResultSet r = jdbcConfig.ExecuteQuery(p);
        listDichVu = FXCollections.observableArrayList();
        while (r.next()) {
            listDichVu.add(new DichVu(r.getString(1), r.getString(2),
                    Integer.parseInt(r.getString(3)),r.getString(4)));
        }
        return listDichVu;
        
    }   
    
    @FXML
    private void SelectRow(MouseEvent e) throws SQLException {
        if (e.getClickCount() == 1) {
            txtMaDichVu.setText(tblDichVu.getSelectionModel()
                    .getSelectedItem().getMaDichVu());
            txtTenDichVu.setText(tblDichVu.getSelectionModel()
                    .getSelectedItem().getTenDichVu());
            txtGia.setText(Integer.toString(tblDichVu.getSelectionModel()
                    .getSelectedItem().getGia()));
            CbbDonViTinh.setValue(new DonViTinh(tblDichVu.getSelectionModel()
                    .getSelectedItem().getDonViTinh()));
        }
    }
    
    private void setDichVu(ObservableList e) throws SQLException{
        tblDichVu.setItems(e);
        tblColMaDichVu.setCellValueFactory(new PropertyValueFactory<>("maDichVu"));
        tblColTenDichVu.setCellValueFactory(new PropertyValueFactory<>("tenDichVu"));
        tblColGia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        tblColDonViTinh.setCellValueFactory(new PropertyValueFactory<>("donViTinh"));
    }
    
    private ObservableList<DonViTinh> getDonViTinh() {
        DonViTinh dvt1 = new DonViTinh("Chai");
        DonViTinh dvt2 = new DonViTinh("Thùng");
        DonViTinh dvt3 = new DonViTinh("Ly");
        DonViTinh dvt4 = new DonViTinh("Phần");
        DonViTinh dvt5 = new DonViTinh("Dĩa");   
        ObservableList<DonViTinh> list = FXCollections.observableArrayList(dvt1,dvt2,dvt3,dvt4,dvt5);
        return list;
    } 
    private void insertDichVu() throws SQLException{
        String sql = String.format("INSERT INTO dichvu(madichvu ,tendichvu ,gia, donvitinh)"
                + "VALUES(? ,? , ? , ?)" );
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtMaDichVu.getText());
        p.setString(2, txtTenDichVu.getText());
        p.setString(3, txtGia.getText());
        p.setString(4, CbbDonViTinh.getValue().toString());
        int rows = jdbcConfig.ExecuteUpdateQuery(p);
        if (rows != 0) {
            setDichVu(getDichVu());
            thongBao();
            alert.setContentText("Thêm Thành Công");
        }
    }
    
    private void deleteDichVu() throws SQLException {
        String sql = String.format("DELETE FROM dichvu\n"
                + "WHERE madichvu = ?");
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtMaDichVu.getText());
        int row = p.executeUpdate();
        if (row == 1) {
            setDichVu(getDichVu());
            thongBao();
            alert.setContentText("Xóa Thành Công");
        }
    }
    
     private void updateDichVu() throws SQLException {
        String sql = String.format("UPDATE dichvu \n"
                + "SET tendichvu = ?,\n"
                + "	      gia = ?,\n"
                + "	donvitinh = ?\n"
                + "WHERE madichvu = ? ");
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtTenDichVu.getText());
        p.setString(2, txtGia.getText());
        p.setString(3, CbbDonViTinh.getValue().toString());
        p.setString(4, txtMaDichVu.getText());
        int row = p.executeUpdate();
        if (row == 1) {
            setDichVu(getDichVu());
            thongBao();
            alert.setContentText("Sửa Thành Công");
        }
    }
    
     private void thongBao(){
        alert.setTitle("Thông Báo");
        alert.setHeaderText(null);
        alert.show();
    }
    private String getMaDichVu() {
        return util.RandomId.createNewID("DV");
    }
    private void getDefaultValue() {
        txtMaDichVu.setText(getMaDichVu());
        txtMaDichVu.setEditable(false);
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jdbcConfig.Connect();
        getDefaultValue();
        try {
            setDichVu(getDichVu());
            ObservableList<DonViTinh> listgt = getDonViTinh();
            CbbDonViTinh.getItems().addAll(listgt);
            CbbDonViTinh.getSelectionModel().select(0); 
            
            btnThem.setOnAction(e ->{
                try {
                    insertDichVu();
                } catch (SQLException ex) {
                    Logger.getLogger(DichVuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            btnXoa.setOnAction(e->{
                try {
                    deleteDichVu();
                } catch (SQLException ex) {
                    Logger.getLogger(DichVuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            btnLuu.setOnAction(e->{
                try {
                    updateDichVu();
                } catch (SQLException ex) {
                    Logger.getLogger(DichVuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(DichVuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
