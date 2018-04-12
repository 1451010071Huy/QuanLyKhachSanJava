/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.controllers;

import config.jdbcConfig;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import restaurant.manager.models.GioiTinh;
import restaurant.manager.models.KhachHang;

/**
 * FXML Controller class
 *
 * @author Luxury
 */
public class KhachHangController implements Initializable {

   
    @FXML
    private TableView<KhachHang> tblKhachHang;
    @FXML
    private TableColumn<KhachHang, String> tblColMaKhachHang;
    @FXML
    private TableColumn<KhachHang, String> tblColTenKhachHang;
    @FXML
    private TableColumn<KhachHang, String> tblColGioiTinh;
    @FXML
    private TableColumn<KhachHang, String> tblColCmnd;
    @FXML
    private TableColumn<KhachHang, String> tblColDiaChi;
    @FXML
    private TableColumn<KhachHang, String> tblColCoQuan;
    @FXML
    private TableColumn<KhachHang, String> tblColSoDienThoai;
    @FXML
    private TableColumn<KhachHang, String> tblColEmail;
    @FXML
    private TextField txtMaKhachHang;
    @FXML
    private TextField txtTenKhachHang;
    @FXML
    private ComboBox<GioiTinh> cbbGioiTinh;
    @FXML
    private TextField txtCmnd;
    @FXML
    private TextField txtCoQuan;
    @FXML
    private TextField txtSoDienThoai;
    @FXML
    private TextField txtDiaChi;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTimKiem;
    @FXML
    private Button btnThemKhachHang;
    @FXML
    private Button btnCapNhatKhachHang;
    @FXML
    private Button btnXoaKhachHang;
    @FXML
    private Button btnHuyKhachHang;
    @FXML
    private Button btnTimKiem;
    private ObservableList<KhachHang> listKhachHang;
    private FilteredList<KhachHang> filteredData;
    
    private ObservableList<GioiTinh> getGioiTinh() {
        GioiTinh g1 = new GioiTinh("Nam");
        GioiTinh g2 = new GioiTinh("Nữ");
        ObservableList<GioiTinh> list
                = FXCollections.observableArrayList(g1, g2);

        return list;
    }

    private ObservableList<KhachHang> getKhachHang() throws SQLException {
        String sql = "SELECT * FROM khachhang";
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);

        ResultSet r = jdbcConfig.ExecuteQuery(p);//Thực thi câu truy vấn
        listKhachHang = FXCollections.observableArrayList();
        filteredData=new FilteredList<>(listKhachHang,e->true);
        while (r.next()) {
            listKhachHang.add(new KhachHang(r.getString(1),
                    r.getString(2), r.getString(3),
                    r.getString(4), r.getString(5), r.getString(6), r.getString(7), r.getString(8)));
        }
        return listKhachHang;
    }

    private void setTableKhachHang(ObservableList e) throws SQLException {
        tblKhachHang.setItems(e);//Lấy giá trị DB rồi set cho bảng 
        tblColMaKhachHang.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));//set mã loại (với mã loại là thuộc tính của model)
        tblColTenKhachHang.setCellValueFactory(new PropertyValueFactory<>("tenKhachHang"));
        tblColGioiTinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        tblColCmnd.setCellValueFactory(new PropertyValueFactory<>("cmnd"));
        tblColDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        tblColCoQuan.setCellValueFactory(new PropertyValueFactory<>("coQuan"));
        tblColSoDienThoai.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        tblColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
    
    @FXML
    private void searchKhachHang()
    {
        txtTimKiem.textProperty().addListener((observableValue,oldValue,newValue)->{
		filteredData.setPredicate((Predicate<? super KhachHang>)KhachHang->{
			if(newValue==null||newValue.isEmpty()){
				return true;
			}
			String lowerCaseFilter=newValue.toLowerCase();
			if(KhachHang.getMaKhachHang().toLowerCase().contains(lowerCaseFilter)){
				return true;
			}
			else if(KhachHang.getTenKhachHang().toLowerCase().contains(lowerCaseFilter)){
				return true;
			}
                        else if(KhachHang.getDiaChi().toLowerCase().contains(lowerCaseFilter)){
				return true;
                        }
                        else if(KhachHang.getSdt().toLowerCase().contains(lowerCaseFilter)){
				return true;
                        }
                        else if(KhachHang.getCmnd().toLowerCase().contains(lowerCaseFilter)){
				return true;
                        }
			return false;
		});
	});
	SortedList<KhachHang> sortedData=new SortedList<>(filteredData);
	sortedData.comparatorProperty().bind(tblKhachHang.comparatorProperty());
	tblKhachHang.setItems(sortedData);
    }  
    
    @FXML
    private void SelectRow(MouseEvent e) throws SQLException {
        if (e.getClickCount() == 1) {
            txtMaKhachHang.setText(tblKhachHang.getSelectionModel()
                    .getSelectedItem().getMaKhachHang());
            txtTenKhachHang.setText(tblKhachHang.getSelectionModel()
                    .getSelectedItem().getTenKhachHang());
            txtCmnd.setText(tblKhachHang.getSelectionModel()
                    .getSelectedItem().getCmnd());
            txtDiaChi.setText(tblKhachHang.getSelectionModel()
                    .getSelectedItem().getDiaChi());
            txtCoQuan.setText(tblKhachHang.getSelectionModel()
                    .getSelectedItem().getCoQuan());
            txtSoDienThoai.setText(tblKhachHang.getSelectionModel()
                    .getSelectedItem().getSdt());
            txtEmail.setText(tblKhachHang.getSelectionModel()
                    .getSelectedItem().getEmail());
            cbbGioiTinh.setValue(new GioiTinh(tblKhachHang.getSelectionModel()
                    .getSelectedItem().getGioiTinh()));
        }
    }

    private void insertKhachHang() throws SQLException {
        String sql = String.format("INSERT INTO khachhang(makhachhang, tenkhachhang, gioitinh,"
                + "cmnd_passport , diachi ,coquan , sodienthoai ,email)"
                + "VALUES (?, ?, ?, ? , ? ,? ,? , ?)");
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtMaKhachHang.getText());
        p.setString(2, txtTenKhachHang.getText());
        p.setString(3, cbbGioiTinh.getValue().toString());
        p.setString(4, txtCmnd.getText());
        p.setString(5, txtDiaChi.getText());
        p.setString(6, txtCoQuan.getText());
        p.setString(7, txtSoDienThoai.getText());
        p.setString(8, txtEmail.getText());
        int rows = jdbcConfig.ExecuteUpdateQuery(p);
        if (rows != 0) {
            setTableKhachHang(getKhachHang());
        }
    }

    private void deleteKhachHang() throws SQLException {
        String sql = String.format("DELETE from khachhang WHERE makhachhang = ?");
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtMaKhachHang.getText());
        int row = p.executeUpdate();
        if (row == 1) {
            setTableKhachHang(getKhachHang());
        }
    }

    private void updateKhachHang() throws SQLException {

        String sql = String.format("UPDATE khachhang SET tenkhachhang = ?,\n"
                + "gioitinh = ?,\n"
                + "cmnd_passport = ?,\n"
                + "diachi = ?,\n"
                + "coquan = ?,\n"
                + "sodienthoai = ?,\n"
                + "email = ?\n"
                + "WHERE makhachhang = ?;"
        );
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtTenKhachHang.getText());
        p.setString(2, cbbGioiTinh.getValue().toString());
        p.setString(3, txtCmnd.getText());
        p.setString(4, txtDiaChi.getText());
        p.setString(5, txtCoQuan.getText());
        p.setString(6, txtSoDienThoai.getText());
        p.setString(7, txtEmail.getText());
        p.setString(8, txtMaKhachHang.getText());
        int row = p.executeUpdate();
        if (row == 1) {
            setTableKhachHang(getKhachHang());
        }

    }
   
//    private ObservableList<KhachHang> searchKhachHang() {
//        try {
//            String sql = "SELECT * FROM khachhang\n"
//                    + "WHERE makhachhang = ? OR tenkhachhang = ? \n"
//                    + "OR cmnd_passport= ? \n"
//                    + "OR sodienthoai = ? OR email=?";
//
//            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
//            p.setString(1, txtTimKiem.getText());
//            p.setString(2, txtTimKiem.getText());
//            p.setString(3, txtTimKiem.getText());
//            p.setString(4, txtTimKiem.getText());
//            p.setString(5, txtTimKiem.getText());
//
//            ResultSet r = jdbcConfig.ExecuteQuery(p);
//            while (r.next()) {
//                listKhachHang.add(new KhachHang(r.getString(1),
//                        r.getString(2), r.getString(3),
//                        r.getString(4), r.getString(5), r.getString(6), r.getString(7), r.getString(8)));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(KhachHangController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return listKhachHang;
//    }

    private void ClearKhachHang() throws SQLException {
        txtMaKhachHang.setText("");
        txtTenKhachHang.setText("");
        txtSoDienThoai.setText("");
        txtDiaChi.setText("");
        txtCoQuan.setText("");
        txtEmail.setText("");
        txtCmnd.setText("");      
    }   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            jdbcConfig.Connect();

            ObservableList<GioiTinh> listgt = getGioiTinh();
            cbbGioiTinh.getItems().addAll(listgt);
            cbbGioiTinh.getSelectionModel().select(0);
            setTableKhachHang(getKhachHang());
            btnThemKhachHang.setOnAction(e -> {
                try {
                    insertKhachHang();
                } catch (SQLException ex) {
                    Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            btnXoaKhachHang.setOnAction(e -> {
                try {
                    deleteKhachHang();
                } catch (SQLException ex) {
                    Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            btnCapNhatKhachHang.setOnAction(e -> {
                try {
                    updateKhachHang();
                } catch (SQLException ex) {
                    Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
            
            btnHuyKhachHang.setOnAction(e->{
                try {
                    ClearKhachHang();
                } catch (SQLException ex) {
                    Logger.getLogger(NhanVienController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
//            btnTimKiem.setOnAction((e) -> {
//                try {
//                    tblKhachHang.getItems().clear();
//                    setTableKhachHang(searchKhachHang());
//                } catch (SQLException ex) {
//                    Logger.getLogger(KhachHangController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            });
                      
        } catch (SQLException ex) {
            Logger.getLogger(KhachHangController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
