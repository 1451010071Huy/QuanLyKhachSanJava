/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.controllers;

import config.jdbcConfig;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import restaurant.manager.models.PhieuDatPhong;
import restaurant.manager.models.Phong;

/**
 * FXML Controller class
 *
 * @author Luxury
 */
public class PhieuDatPhongController implements Initializable {

    @FXML
    private Group groupChecked;

    @FXML
    private TextField txtSoNguoi;

    @FXML
    private Label lblTienCoc;

    @FXML
    private TableColumn<Phong, Double> tblColGia;

    @FXML
    private Button btnDatPhong;

    @FXML
    private TextField txtDatCoc;

    @FXML
    private Label lblNgayDi;

    @FXML
    private DatePicker dpkNgayDen;

    @FXML
    private Label lblMaKhachHang;

    @FXML
    private Button btnTimPhong;

    @FXML
    private ComboBox<String> cbbKhachHang;

    @FXML
    private Label lblGioiTinh;

    @FXML
    private Button btnSua;

    @FXML
    private TableColumn<PhieuDatPhong, String> tblColMaPhieuDatDS;

    @FXML
    private Label lblSoNguoi;

    @FXML
    private TableView<Phong> tblPhongDat;

    @FXML
    private TableColumn<PhieuDatPhong, String> tblColKhachHangDS;

    @FXML
    private TableColumn<PhieuDatPhong, Date> tblColNgayDiDS;

    @FXML
    private Label lblMaPhieuDat;

    @FXML
    private Label lblDienThoai;

    @FXML
    private TableColumn<PhieuDatPhong, String> tblColTinhTrangDS;

    @FXML
    private Label lblNgayDen;

    @FXML
    private Label lblEmail;

    @FXML
    private TableColumn<Phong, String> tblColPhong;

    @FXML
    private Label lblDiaChi;

    @FXML
    private TableColumn<PhieuDatPhong, Integer> tblColSoNguoiDS;

    @FXML
    private TableColumn<Phong, String> tblColLoaiPhong;

    @FXML
    private TableColumn<Phong, Integer> tblColSoNguoi;

    @FXML
    private Label lblTenKhachHang;

    @FXML
    private DatePicker dpkNgayDi;

    @FXML
    private Label lblCoQuan;

    @FXML
    private Label lblCMND;

    @FXML
    private CheckBox chbWaitting;

    @FXML
    private CheckBox chbFinnis;

    @FXML
    private CheckBox chbCancel;

    @FXML
    private TableView<PhieuDatPhong> tblPhieuDatDS;

    @FXML
    private TableColumn<PhieuDatPhong, Date> tblColNgayDenDS;
    /**
     * Initializes the controller class.
     */
    private ObservableList<PhieuDatPhong> listPhieuDatPhong;

    private ObservableList<PhieuDatPhong> getPhieuDatPhong() {
        try {
            String sql = "SELECT maphieudat, makhachhang, ngayden, ngaydi,"
                    + "tinhtrang, songuoi \n"
                    + "FROM phieudatphong\n"
                    + "WHERE tinhtrang = ? OR tinhtrang = ? OR tinhtrang = ?";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, getCheckedWaitting());
            p.setString(2, getCheckedCancel());
            p.setString(3, getCheckedFinish());
            ResultSet r = jdbcConfig.ExecuteQuery(p);
            listPhieuDatPhong = FXCollections.observableArrayList();
            while (r.next()) {
                listPhieuDatPhong.add(new PhieuDatPhong(r.getString(1),
                        r.getString(2), r.getString(3),
                        r.getString(4),
                        r.getString(5), Integer.parseInt(r.getString(6))
                ));
            }

        } catch (SQLException ex) {
            System.out.println("Lỗi không xuất được phiếu đặt phòng " + ex);
        }
        return listPhieuDatPhong;
    }

    private String getCheckedFinish() {

        if (chbFinnis.isSelected()) {
            return "finish";
        } else {
            return "";
        }

    }

    private String getCheckedCancel() {
        if (chbCancel.isSelected()) {
            return "cancel";
        } else {
            return "";
        }

    }

    private String getCheckedWaitting() {
        if (chbWaitting.isSelected()) {
            return "waitting";
        } else {
            return "";
        }

    }

    private void getTenKhachHang() throws SQLException {
        String sql = "SELECT tenkhachhang, makhachhang FROM khachhang";
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        ResultSet r = jdbcConfig.ExecuteQuery(p);
        while (r.next()) {
            cbbKhachHang.getItems().add(r.getString(1));
        }
    }

    @FXML
    private void setKhachHang(ActionEvent e) throws SQLException {

        String sql = "SELECT * FROM khachhang WHERE tenkhachhang = ?";
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, cbbKhachHang.getValue());
        ResultSet r = jdbcConfig.ExecuteQuery(p);
        while (r.next()) {
            lblMaKhachHang.setText(r.getString(1));
            lblTenKhachHang.setText(r.getString(2));
            if(null == r.getString(3)){
                lblGioiTinh.setText("");
            }else switch (r.getString(3)) {
                case "1":
                    lblGioiTinh.setText("Nam");
                    break;
                case "0":
                    lblGioiTinh.setText("Nữ");
                    break;
                default:
                    lblGioiTinh.setText("");
                    break;
            }
            lblCMND.setText(r.getString(4));
            lblDiaChi.setText(r.getString(5));
            lblCoQuan.setText(r.getString(6));
            lblDienThoai.setText(r.getString(7));
            lblEmail.setText(r.getString(8));
        }
        setTableDatPhong();
    }

    private void setTablePhieuDatPhong() throws SQLException {
        tblPhieuDatDS.setItems(getPhieuDatPhong());//Lấy giá trị DB rồi set cho bảng 
        tblColMaPhieuDatDS.setCellValueFactory(new PropertyValueFactory<>("maPhieuDat"));
        tblColKhachHangDS.setCellValueFactory(new PropertyValueFactory<>("maKhachHang"));
        tblColNgayDenDS.setCellValueFactory(new PropertyValueFactory<>("ngayDen"));
        tblColNgayDiDS.setCellValueFactory(new PropertyValueFactory<>("ngayDi"));
        tblColSoNguoiDS.setCellValueFactory(new PropertyValueFactory<>("soNguoi"));
        tblColTinhTrangDS.setCellValueFactory(new PropertyValueFactory<>("tinhTrang"));

    }

    private void eventChanged(){
        chbWaitting.setOnAction(e -> {
            try {
                setTablePhieuDatPhong();
            } catch (SQLException ex) {
                Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        chbFinnis.setOnAction(e -> {
            try {
                setTablePhieuDatPhong();
            } catch (SQLException ex) {
                Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        chbCancel.setOnAction(e -> {
            try {
                setTablePhieuDatPhong();
            } catch (SQLException ex) {
                Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        txtSoNguoi.textProperty().addListener((e) -> {
            lblSoNguoi.setText(txtSoNguoi.getText());
        });
        txtDatCoc.textProperty().addListener((e) -> {
            lblTienCoc.setText(txtDatCoc.getText());
        });
        dpkNgayDen.setOnAction(e -> {
            if (null != dpkNgayDen.getValue()) {
                lblNgayDen.setText(dpkNgayDen.getValue().toString());
            }
        });
        dpkNgayDi.setOnAction(e -> {
            if (null != dpkNgayDi.getValue()) {
                lblNgayDi.setText(dpkNgayDi.getValue().toString());
            }
        });
    }
    private ObservableList<Phong> listThongTinDatPhong;

    private void setTableDatPhong() throws SQLException {
        try {
            //      tblPhongDat.setItems(getThongTinDatPhong());//Lấy giá trị DB rồi set cho bảng
            tblColPhong.setCellValueFactory(new PropertyValueFactory<>("maPhong"));
            tblColLoaiPhong.setCellValueFactory(new PropertyValueFactory<>("maLoai"));
            tblColGia.setCellValueFactory(new PropertyValueFactory<>("gia"));
            tblColSoNguoi.setCellValueFactory(new PropertyValueFactory<>("soNguoi"));
        } catch (Exception ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Hàm này kiểm tra ngày đến và ngày đi
     */
    private void checkDatepicker() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (null == dpkNgayDen.getValue() || null == dpkNgayDi.getValue()) {
            alert.setTitle("Thông báo");
            alert.setHeaderText("Ngày đến và ngày đi không được để trống");
            alert.setContentText("Vui lòng nhập ngày đến và ngày đi");
            alert.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnTimPhong.setOnAction(e -> {
            checkDatepicker();
        });
        try {
            jdbcConfig.Connect(); // connect database
            setTablePhieuDatPhong();
            getTenKhachHang();
            eventChanged();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
