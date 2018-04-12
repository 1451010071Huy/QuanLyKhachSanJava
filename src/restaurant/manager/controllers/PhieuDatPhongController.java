/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.controllers;

import config.jdbcConfig;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.input.MouseEvent;
import restaurant.manager.models.KiemTraPhong;
import restaurant.manager.models.PhieuDatPhong;
import restaurant.manager.models.Phong;

/**
 * FXML Controller class
 *
 * @author Luxury
 */
public class PhieuDatPhongController implements Initializable {

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
    private TableColumn<PhieuDatPhong, Timestamp> tblColNgayDiDS;
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
    private TableColumn<PhieuDatPhong, Timestamp> tblColNgayDenDS;
    @FXML
    private TableView<Phong> tblPhongTrong;
    @FXML
    private TableColumn<Phong, String> tblColPhongPT;
    @FXML
    private TableColumn<Phong, String> tblColLoaiPhongPT;
    @FXML
    private TableColumn<Phong, Integer> tblColSoNguoiPT;
    @FXML
    private TableColumn<Phong, Double> tblColGiaPT;
    @FXML
    private CheckBox chbBusy;
    @FXML
    private Button btnNhanPhong;
    @FXML
    private TableColumn<?, ?> tblColChonPhong;
    @FXML
    private TableColumn<?, ?> tblColXoaPhong;

    private ObservableList<PhieuDatPhong> listPhieuDatPhong = null;
    private ObservableList<Phong> listPhongDat = null;
    private ObservableList<KiemTraPhong> listPhongKT = null;
    private ObservableList<Phong> listPhong = null;

    private ObservableList<PhieuDatPhong> getPhieuDatPhong() {
        try {
            String sql = "SELECT maphieudat, p.makhachhang , ngayden, ngaydi,"
                    + "tinhtrang, songuoi, sotiendatcoc, tenkhachhang\n"
                    + "FROM phieudatphong as p, khachhang as k\n"
                    + "WHERE p.makhachhang = k.makhachhang AND\n"
                    + "(tinhtrang = ? OR tinhtrang = ? OR tinhtrang = ? \n"
                    + "OR tinhtrang = ?)";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, getCheckedWaitting());
            p.setString(2, getCheckedCancel());
            p.setString(3, getCheckedFinish());
            p.setString(4, getCheckedBusy());
            ResultSet r = jdbcConfig.ExecuteQuery(p);
            listPhieuDatPhong = FXCollections.observableArrayList();// Khởi chạy Observable

            while (r.next()) {

                listPhieuDatPhong.add(new PhieuDatPhong(
                        r.getString(1),
                        r.getString(2),
                        r.getTimestamp(3),
                        r.getTimestamp(4),
                        r.getString(5),
                        r.getInt(6),
                        r.getDouble(7),
                        r.getString(8)
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi không xuất được phiếu đặt phòng " + ex);
        }

        return listPhieuDatPhong;
    }

    private ObservableList<Phong> getChiTietDatPhong() {
        try {
            String sql = "SELECT p.maphong, lp.maloai, gia, songuoi \n"
                    + "FROM chitietdatphong as c, phong as p, loaiphong as lp\n"
                    + "WHERE c.maphong = p.maphong AND maphieudat = ?\n"
                    + "AND lp.maloai = p.maloai";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, lblMaPhieuDat.getText());
            ResultSet r = jdbcConfig.ExecuteQuery(p);
            listPhongDat = FXCollections.observableArrayList();
            while (r.next()) {
                listPhongDat.add(new Phong(r.getString(1), r.getString(2),
                        r.getDouble(3), r.getInt(4)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPhongDat;
    }

    private String getCheckedWaitting() {
        if (chbWaitting.isSelected()) {
            return "waitting";
        }
        return "";
    }

    private String getCheckedBusy() {
        if (chbBusy.isSelected()) {
            return "busy";
        }
        return "";
    }

    private String getCheckedCancel() {
        if (chbCancel.isSelected()) {
            return "cancel";
        }
        return "";
    }

    private String getCheckedFinish() {
        if (chbFinnis.isSelected()) {
            return "finish";
        }
        return "";
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
            if (null == r.getString(3)) {
                lblGioiTinh.setText("");
            } else {
                switch (r.getString(3)) {
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
            }
            lblCMND.setText(r.getString(4));
            lblDiaChi.setText(r.getString(5));
            lblCoQuan.setText(r.getString(6));
            lblDienThoai.setText(r.getString(7));
            lblEmail.setText(r.getString(8));
        }
    }

    private void setTablePhieuDatPhong() throws SQLException {
        Map<TableColumn, String> mapCol = new HashMap<>();
        mapCol.put(tblColMaPhieuDatDS, "maPhieuDat");
        mapCol.put(tblColKhachHangDS, "maKhachHang");
        mapCol.put(tblColNgayDenDS, "ngayDen");
        mapCol.put(tblColNgayDiDS, "ngayDi");
        mapCol.put(tblColSoNguoiDS, "soNguoi");
        mapCol.put(tblColTinhTrangDS, "tinhTrang");
        jdbcConfig.setTableView(tblPhieuDatDS, mapCol, getPhieuDatPhong());
    }

    private void setTableChiTietDatPhong() throws SQLException {
        Map<TableColumn, String> mapCol = new HashMap<>();
        mapCol.put(tblColPhong, "maPhong");
        mapCol.put(tblColLoaiPhong, "maLoai");
        mapCol.put(tblColSoNguoi, "soNguoi");
        mapCol.put(tblColGia, "gia");
        mapCol.put(tblColXoaPhong, "xoaPhong");
        jdbcConfig.setTableView(tblPhongDat, mapCol, getChiTietDatPhong());
    }

    private void setTablePhong() {
        Map<TableColumn, String> mapCol = new HashMap<>();
        mapCol.put(tblColPhongPT, "maPhong");
        mapCol.put(tblColLoaiPhongPT, "maLoai");
        mapCol.put(tblColSoNguoiPT, "soNguoi");
        mapCol.put(tblColGiaPT, "gia");
        mapCol.put(tblColChonPhong, "chonPhong");
        try {
            jdbcConfig.setTableView(tblPhongTrong, mapCol, getPhongTrong());
        } catch (SQLException ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eventChanged() {
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

    private ObservableList<KiemTraPhong> kiemTraPhong(LocalDate ngayDen, LocalDate ngayDi) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String ngDen = ngayDen.format(formatter);
            String ngDi = ngayDi.format(formatter);
            listPhongKT = FXCollections.observableArrayList();
            CallableStatement command = jdbcConfig.connection.prepareCall("{call kiemtraphong (?, ?)}");
            command.setString(1, ngDen);
            command.setString(2, ngDi);
            ResultSet r = jdbcConfig.ExecuteQuery(command);
            while (r.next()) {
                listPhongKT.add(new KiemTraPhong(r.getString(2)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listPhongKT;
    }

    private Boolean locPhong(String maphong, LocalDate ngayden, LocalDate ngaydi) {
        ObservableList<KiemTraPhong> dsPhong = this.kiemTraPhong(ngayden, ngaydi);
        for (KiemTraPhong phong : dsPhong) {
            if (phong.getMaPhong().equals(maphong)) {
                return false;
            }
        }
        return true;
    }

    private ObservableList<Phong> getPhongTrong() throws SQLException {
        try {
            String sql = "SELECT p.maphong, lp.maloai, lp.gia, lp.songuoi\n"
                    + "FROM loaiphong as lp, phong as p\n"
                    + "where lp.maloai = p.maloai";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);

            ResultSet r = jdbcConfig.ExecuteQuery(p);//Thực thi câu truy vấn
            listPhong = FXCollections.observableArrayList();
            int soLuongPhong = 0;
            while (r.next()) {
                if (locPhong(r.getString(1), dpkNgayDen.getValue(), dpkNgayDi.getValue())) {
                    soLuongPhong++;
                    listPhong.add(new Phong(r.getString(1),
                            r.getString(2), r.getDouble(3), r.getInt(4)));
                }
            }
            String soPhong = String.format("Mã phòng (%d)", soLuongPhong);
            tblColPhongPT.setText(soPhong);
        } catch (SQLException ex) {
            Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listPhong;
    }

    private void checkDatepicker() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (null == dpkNgayDen.getValue() || null == dpkNgayDi.getValue()) {
            alert.setTitle("Thông báo");
            alert.setHeaderText("Ngày đến và ngày đi không được để trống");
            alert.setContentText("Vui lòng nhập ngày đến và ngày đi");
            alert.show();
        } else if (dpkNgayDen.getValue().toEpochDay() > dpkNgayDi.getValue().toEpochDay()) {
            alert.setTitle("Thông báo");
            alert.setHeaderText("Ngày đi phải lớn hơn ngày đến");
            alert.setContentText("Vui lòng nhập lại ngày đến và ngày đi");
            alert.show();
        } else {
            setTablePhong();
        }
    }

    @FXML
    public void chonPhong(ActionEvent e) {
        if (listPhong != null) {
            ObservableList<Phong> ob;
            ob = FXCollections.observableArrayList();
            listPhong.forEach((Phong value) -> {
                if (value.getChonPhong().isSelected() == true) {
                    ob.add(value);
                    value.getXoaPhong().setOnAction((ActionEvent event) -> {
                        ob.remove(value);
                    });
                }
            });
            Map<TableColumn, String> mapCol = new HashMap<>();
            mapCol.put(tblColPhong, "maPhong");
            mapCol.put(tblColLoaiPhong, "maLoai");
            mapCol.put(tblColSoNguoi, "soNguoi");
            mapCol.put(tblColGia, "gia");
            mapCol.put(tblColXoaPhong, "xoaPhong");
            jdbcConfig.setTableView(tblPhongDat, mapCol, ob);
        }
    }

    @FXML
    public void clickItemPhieuDat(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 1) //Checking double click
        {
            lblMaPhieuDat.setText(tblPhieuDatDS.getSelectionModel()
                    .getSelectedItem().getMaPhieuDat());
            cbbKhachHang.setValue(tblPhieuDatDS.getSelectionModel()
                    .getSelectedItem().getTenKH());
            txtSoNguoi.setText(String.valueOf(tblPhieuDatDS.getSelectionModel()
                    .getSelectedItem().getSoNguoi()));
            txtDatCoc.setText(String.valueOf(tblPhieuDatDS.getSelectionModel()
                    .getSelectedItem().getSoTienCoc()));
            dpkNgayDen.setValue(tblPhieuDatDS.getSelectionModel()
                    .getSelectedItem().getNgayDen().toLocalDateTime().toLocalDate());
            dpkNgayDi.setValue(tblPhieuDatDS.getSelectionModel()
                    .getSelectedItem().getNgayDi().toLocalDateTime().toLocalDate());
            setTableChiTietDatPhong();
            
            
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jdbcConfig.Connect(); // connect database
        try {
            btnTimPhong.setOnAction(e -> {
                tblPhongDat.getItems().clear();//clear danh sach phong dat
                this.checkDatepicker();
            });
            this.setTablePhieuDatPhong();
            this.getTenKhachHang();
            this.eventChanged();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
