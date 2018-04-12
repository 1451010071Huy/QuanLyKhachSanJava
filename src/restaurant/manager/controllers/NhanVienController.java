/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.controllers;

import config.jdbcConfig;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import restaurant.manager.models.ChucVu;
import restaurant.manager.models.GioiTinh;
import restaurant.manager.models.NhanVien;

/**
 * FXML Controller class
 *
 * @author Luxury
 */
public class NhanVienController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TableView<NhanVien> tblNhanVien;
    @FXML
    TableColumn<NhanVien, String> tblColMaNhanVien;
    @FXML
    TableColumn<NhanVien, String> tblColTenNhanVien;
    @FXML
    TableColumn<NhanVien, Date> tblColNgaySinh;
    @FXML
    TableColumn<NhanVien, String> tblColGioiTinh;
    @FXML
    TableColumn<NhanVien, String> tblColDiaChi;
    @FXML
    TableColumn<NhanVien, String> tblColSoDienThoai;
    @FXML
    TableColumn<NhanVien, String> tblColChucVu;
    @FXML
    TextField txtMaNhanVien;
    @FXML
    TextField txtTenNhanVien;
    @FXML
    DatePicker dpkNgaySinh;
    @FXML
    TextField txtDiaChi;
    @FXML
    ComboBox<ChucVu> cbbChucVu;
    @FXML
    ComboBox<GioiTinh> cbbGioiTinh;
    @FXML
    TextField txtSoDienThoai;
    @FXML
    TextField txtTimKiem;
    @FXML
    Button btnThem;
    @FXML
    Button btnCapNhat;
    @FXML
    Button btnXoa;
    @FXML
    Button btnHuy;
    private ObservableList<NhanVien> listNhanVien;
    private FilteredList<NhanVien> filteredData;
    private ObservableList<NhanVien> getNhanVien() throws SQLException {
        String sql = "SELECT * FROM nhanvien";
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        listNhanVien = FXCollections.observableArrayList();
        filteredData=new FilteredList<>(listNhanVien,e->true);
        ResultSet r = jdbcConfig.ExecuteQuery(p);//Thực thi câu truy vấn
        while (r.next()) {
            listNhanVien.add(new NhanVien(r.getString(1), r.getString(2),
                    r.getTimestamp(3), r.getString(4),
                    r.getString(5), r.getString(6), r.getString(7)));
        }
      
        p.close();
	r.close();
        return listNhanVien;
    }

    private void setNhanVien(ObservableList e) throws SQLException {  
        tblNhanVien.setItems(e);
        tblColMaNhanVien.setCellValueFactory(new PropertyValueFactory<>("maNhanVien"));
        tblColTenNhanVien.setCellValueFactory(new PropertyValueFactory<>("tenNhanVien"));
        tblColNgaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        tblColGioiTinh.setCellValueFactory(new PropertyValueFactory<>("gioiTinh"));
        tblColDiaChi.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        tblColSoDienThoai.setCellValueFactory(new PropertyValueFactory<>("soDienThoai"));
        tblColChucVu.setCellValueFactory(new PropertyValueFactory<>("chucVu"));
    }

    private ObservableList<ChucVu> getChucVu() {
        ChucVu cv1 = new ChucVu("admin");
        ChucVu cv2 = new ChucVu("Nhân Viên");
        ChucVu cv3 = new ChucVu("Quản Lý");
        ObservableList<ChucVu> list = FXCollections.observableArrayList(cv1, cv2, cv3);
        return list;
    }

    private ObservableList<GioiTinh> getGioiTinh() {
        GioiTinh g1 = new GioiTinh("Nam");
        GioiTinh g2 = new GioiTinh("Nữ");
        ObservableList<GioiTinh> list = FXCollections.observableArrayList(g1, g2);

        return list;
    }

    @FXML
    private void SelectRow(MouseEvent e) throws SQLException {
        if (e.getClickCount() == 1) {
            try {
		 txtTenNhanVien.setText(tblNhanVien.getSelectionModel()
                    .getSelectedItem().getTenNhanVien());
                 txtDiaChi.setText(tblNhanVien.getSelectionModel()
                    .getSelectedItem().getDiaChi());
                 txtSoDienThoai.setText(tblNhanVien.getSelectionModel()
                    .getSelectedItem().getSoDienThoai());
                 dpkNgaySinh.setValue(tblNhanVien.getSelectionModel()
                    .getSelectedItem().getNgaySinh().toLocalDateTime().toLocalDate());
                 cbbChucVu.setValue(new ChucVu(tblNhanVien.getSelectionModel()
                    .getSelectedItem().getChucVu()));
                 cbbGioiTinh.setValue(new GioiTinh(tblNhanVien.getSelectionModel()
                    .getSelectedItem().getGioiTinh()));
                 txtMaNhanVien.setText(tblNhanVien.getSelectionModel()
                    .getSelectedItem().getMaNhanVien());	
            } catch (NullPointerException ex) {
                 System.out.println("Exception in TechmasterNPE1()");
                 throw ex;
            }       
        }
    }

    /**
     *
     */
    @FXML
    private void searchNhanVien()
    {
        txtTimKiem.textProperty().addListener((observableValue,oldValue,newValue)->{
		filteredData.setPredicate((Predicate<? super NhanVien>)NhanVien->{
			if(newValue==null||newValue.isEmpty()){
				return true;
			}
			String lowerCaseFilter=newValue.toLowerCase();
			if(NhanVien.getMaNhanVien().toLowerCase().contains(lowerCaseFilter)){
				return true;
			}
			else if(NhanVien.getTenNhanVien().toLowerCase().contains(lowerCaseFilter)){
				return true;
			}
                        else if(NhanVien.getDiaChi().toLowerCase().contains(lowerCaseFilter)){
				return true;
                        }
                        else if(NhanVien.getSoDienThoai().toLowerCase().contains(lowerCaseFilter)){
				return true;
                        }
			return false;
		});
	});
	SortedList<NhanVien> sortedData=new SortedList<>(filteredData);
	sortedData.comparatorProperty().bind(tblNhanVien.comparatorProperty());
	tblNhanVien.setItems(sortedData);
    }  
    
    private void insertNhanVien() throws SQLException {
        String sql = String.format("INSERT INTO nhanvien(manhanvien ,tennhanvien ,ngaysinh "
                + ",phai , diachi ,phone ,chucvu ) VALUES(? ,? , ? , ? , ?, ?, ?)");
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtMaNhanVien.getText());
        p.setString(2, txtTenNhanVien.getText());
        p.setString(3, dpkNgaySinh.getValue().toString());
        p.setString(4, cbbGioiTinh.getValue().toString());
        p.setString(5, txtDiaChi.getText());
        p.setString(6, txtSoDienThoai.getText());
        p.setString(7, cbbChucVu.getValue().toString());
        int rows = jdbcConfig.ExecuteUpdateQuery(p);
        if (rows != 0) {
            setNhanVien(getNhanVien());
        }
    }

    private void deleteKhachHang() throws SQLException {
        String sql = String.format("DELETE FROM nhanvien WHERE manhanvien = ?");
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtMaNhanVien.getText());
        int row = p.executeUpdate();
        if (row == 1) {
            setNhanVien(getNhanVien());
        }
    }
 
    private void updateNhanVien() throws SQLException {
        String sql = String.format("UPDATE nhanvien \n"
                + "SET tennhanvien = ? ,\n"
                + "	ngaysinh = ?, \n"
                + "	phai= ?,\n"
                + "	diachi = ?,\n"
                + "	phone = ?,\n"
                + "	chucvu = ?\n"
                + "WHERE manhanvien = ?;"
        );
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtTenNhanVien.getText());
        p.setString(2, dpkNgaySinh.getValue().toString());
        p.setString(3, cbbGioiTinh.getValue().toString());
        p.setString(4, txtDiaChi.getText());
        p.setString(5, txtSoDienThoai.getText());
        p.setString(6, cbbChucVu.getValue().toString());
        p.setString(7, txtMaNhanVien.getText());
        int row = p.executeUpdate();
        if (row == 1) {
            setNhanVien(getNhanVien());
        }
    }
    private void ClearNhanVien() throws SQLException {
        txtMaNhanVien.setText("");
        txtTenNhanVien.setText("");
        txtSoDienThoai.setText("");
        txtDiaChi.setText("");
        dpkNgaySinh.setValue(null);
    }   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jdbcConfig.Connect();
        try {

            ObservableList<GioiTinh> listgt = getGioiTinh();
            cbbGioiTinh.getItems().addAll(listgt);
            cbbGioiTinh.getSelectionModel().select(0);

            ObservableList<ChucVu> listcv = getChucVu();
            cbbChucVu.getItems().addAll(listcv);
            cbbChucVu.getSelectionModel().select(0);

            setNhanVien(getNhanVien());

            btnThem.setOnAction(e -> {
                try {
                    insertNhanVien();
                } catch (SQLException ex) {
                    Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            btnXoa.setOnAction(e -> {
                try {
                    deleteKhachHang();

                } catch (SQLException ex) {
                    Logger.getLogger(NhanVienController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            btnCapNhat.setOnAction(e -> {
                try {
                    updateNhanVien();
                } catch (SQLException ex) {
                    Logger.getLogger(NhanVienController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
            btnHuy.setOnAction(e->{
                try {
                    ClearNhanVien();
                } catch (SQLException ex) {
                    Logger.getLogger(NhanVienController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
