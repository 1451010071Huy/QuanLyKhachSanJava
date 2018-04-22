/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.controllers;

import config.jdbcConfig;
import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import restaurant.manager.models.KiemTraPhong;
import restaurant.manager.models.PhieuDatPhong;
import restaurant.manager.models.Phong;
import static util.AlertCustom.*;

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
    private CheckBox chbFinish;
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
    private TableColumn<Phong, CheckBox> tblColChonPhong;
    @FXML
    private TableColumn<Phong, Button> tblColXoaPhong;
    @FXML
    private TabPane tabIndex;
    @FXML
    private Tab tabIndex2;

    private ObservableList<PhieuDatPhong> listPhieuDatPhong = null;
    private ObservableList<Phong> listPhongDat = null;
    private ObservableList<KiemTraPhong> listPhongKT = null;
    private ObservableList<Phong> listPhong = null;

    private final String WAITTING = "waitting";
    private final String BUSY = "busy";
    private final String CANCEL = "cancel";
    private final String FINISH = "finish";
    public String user = "admin";
    @FXML
    private Tab tabIndex1;
    @FXML
    private Button btnNhanPhong;

    private ObservableList<PhieuDatPhong> getPhieuDatPhong() {

        try {
            PreparedStatement p;
            String sql = "SELECT maphieudat, p.makhachhang , ngayden, ngaydi,"
                    + "tinhtrang, songuoi, sotiendatcoc, tenkhachhang\n"
                    + "FROM phieudatphong as p, khachhang as k\n"
                    + "WHERE p.makhachhang = k.makhachhang AND\n"
                    + "(tinhtrang = ? OR tinhtrang = ? OR tinhtrang = ? \n"
                    + "OR tinhtrang = ?)";
            p = jdbcConfig.connection.prepareStatement(sql);
            listPhieuDatPhong = FXCollections.observableArrayList();// Khởi chạy Observable
            p.setString(1, getCheckedWaitting());
            p.setString(2, getCheckedCancel());
            p.setString(3, getCheckedFinish());
            p.setString(4, getCheckedBusy());
            ResultSet r = jdbcConfig.ExecuteQuery(p);
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
            p.close();
        } catch (SQLException ex) {
            System.out.println("Lỗi không xuất được phiếu đặt phòng " + ex);
        }

        return listPhieuDatPhong;
    }

    private ObservableList<Phong> getChiTietDatPhongById(String maPhieuDat) {
        try {
            String sql = "SELECT p.maphong, lp.maloai, gia, songuoi \n"
                    + "FROM chitietdatphong as c, phong as p, loaiphong as lp\n"
                    + "WHERE c.maphong = p.maphong AND maphieudat = ?\n"
                    + "AND lp.maloai = p.maloai";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, maPhieuDat);
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
            return WAITTING;
        }
        return "";
    }

    private String getCheckedBusy() {
        if (chbBusy.isSelected()) {
            return BUSY;
        }
        return "";
    }

    private String getCheckedCancel() {
        if (chbCancel.isSelected()) {
            return CANCEL;
        }
        return "";
    }

    private String getCheckedFinish() {
        if (chbFinish.isSelected()) {
            return FINISH;
        }
        return "";
    }

    private String getMaPhieuDat() {
        return tblPhieuDatDS.getSelectionModel().getSelectedItem().getMaPhieuDat();
    }

    private String getTingTrang() {
        return tblPhieuDatDS.getSelectionModel().getSelectedItem().getTinhTrang();
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

    private void setTablePhieuDatPhong() {
        tblPhieuDatDS.getItems().clear();
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

        listPhongDat = getChiTietDatPhongById(lblMaPhieuDat.getText());

        listPhongDat.forEach(value -> {
            value.getXoaPhong().setOnAction(e -> {
                Optional<ButtonType> result = setAlertConf(
                        "Mã phòng : " + value.getMaPhong(),
                        "Bạn có muốn xóa không"
                );
                if (result.get() == ButtonType.YES) {
                    listPhongDat.remove(value);
                    deletePhongDaDat(value);
                }
            });
        });
        jdbcConfig.setTableView(tblPhongDat, mapCol, listPhongDat);
    }

    private void deletePhongDaDat(Phong phong) {
        try {
            String sql = "DELETE FROM chitietdatphong\n"
                    + "WHERE maphieudat = ? AND maphong = ?";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, lblMaPhieuDat.getText());
            p.setString(2, phong.getMaPhong());

            int i = jdbcConfig.ExecuteUpdateQuery(p);
            if (i == 1) {
                p.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            setTablePhieuDatPhong();

        });
        chbFinish.setOnAction(e -> {
            setTablePhieuDatPhong();

        });
        chbBusy.setOnAction(e -> {
            setTablePhieuDatPhong();

        });
        chbCancel.setOnAction(e -> {
            setTablePhieuDatPhong();

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
            CallableStatement command = jdbcConfig.connection.prepareCall("{call KiemTraPhong (?, ?)}");
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

    private boolean checkInputText() {
        if (cbbKhachHang.getValue() == null) {
            setAlertInfo(
                    "Thông báo",
                    "Bạn chưa chọn khách hàng",
                    "Vui lòng chọn khách hàng");
            return false;
        } else if ("".equals(txtSoNguoi.getText())) {
            setAlertInfo(
                    "Thông báo",
                    "Số lượng khách hàng không được để trống",
                    "Vui lòng nhập số lượng khách hàng");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkDatepicker() {
        if (null == dpkNgayDen.getValue() || null == dpkNgayDi.getValue()) {
            setAlertInfo(
                    "Thông báo",
                    "Ngày đến và ngày đi không được để trống",
                    "Vui lòng nhập ngày đến và ngày đi");
            return false;
        } else if (dpkNgayDen.getValue().toEpochDay() > dpkNgayDi.getValue().toEpochDay()) {
            setAlertInfo(
                    "Thông báo",
                    "Ngày đi phải lớn hơn ngày đến",
                    "Vui lòng nhập lại ngày đến và ngày đi");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkThongTinDatPhong() {
        if (false == checkDatepicker() || checkInputText() == false) {
            setAlertInfo("Thông báo", "Đặt phòng không thành công",
                    "Xin vui lòng nhập đầy đủ thông tin");
            return false;
        } else if (tblPhongDat.getItems().isEmpty()) {

            setAlertInfo("Thông báo", "Bạn chưa chọn phòng đặt",
                    "Vui lòng chọn phòng để đặt");
            return false;
        } else {
            return true;
        }
    }

    @FXML
    public void chonPhong(ActionEvent e
    ) {
        tabIndex.getSelectionModel().select(tabIndex1);//focus tab chi tiet
        if (listPhong != null) {
            ObservableList<Phong> ob;
            ob = FXCollections.observableArrayList();
            listPhong.forEach((Phong value) -> {
                if (value.getChonPhong().isSelected() == true) {
                    ob.add(value);
                    value.getXoaPhong().setOnAction((ActionEvent event) -> {
                        Optional<ButtonType> result = setAlertConf("Mã phòng : " + value.getMaPhong(),
                                "Bạn có muốn xóa không");
                        if (result.get() == ButtonType.YES) {
                            ob.remove(value);
                        }
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
    public void selectItem(MouseEvent e) {
        try {
            if (tblPhieuDatDS.getSelectionModel().getSelectedItem() != null) {
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
        } catch (SQLException ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getNgayDen() {
        return dpkNgayDen.getValue().toString() + " 12:00:00";
    }

    private String getNgayDi() {
        return dpkNgayDi.getValue().toString() + " 12:00:00";
    }

    private int insertChiTietDatPhong(String id) throws SQLException {
        int i = 0;
        PreparedStatement pInsertPhong;
        String query = "IF NOT EXISTS(select * from chitietdatphong "
                + "where maphieudat=? AND maphong=?)\n"
                + "insert into chitietdatphong(maphieudat, maphong) values(?,?)";
        for (Phong item : tblPhongDat.getItems()) {
            pInsertPhong = jdbcConfig.connection.prepareStatement(query);
            pInsertPhong.setString(1, id);
            pInsertPhong.setString(2, item.getMaPhong());
            pInsertPhong.setString(3, id);
            pInsertPhong.setString(4, item.getMaPhong());
            i = jdbcConfig.ExecuteUpdateQuery(pInsertPhong);
        }

        return i;
    }
    
    private void insertTrangThaiPhong(String idPhong){
        String sql = "INSERT INTO trangthaiphong(maphong,trangthai) ";
    }
    
    @FXML
    public void datPhong(ActionEvent a) throws SQLException {
        try {
            if (checkThongTinDatPhong()) {
                jdbcConfig.connection.setAutoCommit(false);
                String id = util.RandomId.createNewID("PD");
                String sql = "INSERT INTO phieudatphong("
                        + "maphieudat, makhachhang,ngayden, ngaydi, "
                        + "sotiendatcoc, username, tinhtrang, songuoi)\n"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pInsertPDP;
                pInsertPDP = jdbcConfig.connection.prepareStatement(sql);
                pInsertPDP.setString(1, id);
                pInsertPDP.setString(2, lblMaKhachHang.getText());
                pInsertPDP.setString(3, getNgayDen());
                pInsertPDP.setString(4, getNgayDi());
                pInsertPDP.setString(5, txtDatCoc.getText());
                pInsertPDP.setString(6, user);
                pInsertPDP.setString(7, WAITTING);
                pInsertPDP.setString(8, txtSoNguoi.getText());

                int r = pInsertPDP.executeUpdate();
                if (r == 1) {
                    pInsertPDP.close();
                    int i = insertChiTietDatPhong(id);
                    if (i == 1) {
                        util.AlertCustom.setAlertInfo("Thông báo",
                                "Đặt phòng thành công", "Mã phiếu : " + id
                                + "\nKhách hàng : " + lblTenKhachHang.getText()
                                + "\nNgày đặt : " + dpkNgayDen.getValue().toString());
                        tblPhongTrong.getItems().clear();// xóa tất cả danh sách phòng trống            
                        tabIndex.getSelectionModel().select(tabIndex2);//focus tab phiếu đặt phòng
                        Platform.runLater(() -> {
                            tblPhieuDatDS.requestFocus();
                            tblPhieuDatDS.getSelectionModel().select(0);
                            tblPhieuDatDS.getFocusModel().focus(0);
                        });
                        clearInput();
                    }
                    jdbcConfig.connection.commit();
                    setTablePhieuDatPhong();

                    PhieuDatPhong pdp = new PhieuDatPhong(id, lblMaKhachHang.getText(),
                            Integer.valueOf(txtSoNguoi.getText()), Timestamp.valueOf(getNgayDen()),
                            Timestamp.valueOf(getNgayDi()), WAITTING);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearInput() {
        cbbKhachHang.setValue("--- Chọn khách hàng ---");
        lblMaPhieuDat.setText("------");
        txtSoNguoi.setText("0");
        txtDatCoc.setText("0");
        tblPhongTrong.getItems().clear();
        tblPhongDat.getItems().clear();
        dpkNgayDen.setValue(LocalDate.now());
        setDefaltDpk();
    }

    @FXML
    private void huyDatPhong(ActionEvent e) throws SQLException {
        if (!"------".equals(lblMaPhieuDat.getText())) {
            if (!getTingTrang().equals(WAITTING)) {
                setAlertInfo("Thông báo", "Hủy không thành công",
                        "Phiếu đặt phòng phải là " + WAITTING);
            } else {
                Optional<ButtonType> alert = setAlertConf("Phiếu đặt phòng : "
                        + lblMaPhieuDat.getText(),
                        "Thao tác này không thể hoàn tác,"
                        + " \nbạn có muốn hủy không!"
                );
                if (alert.get() == ButtonType.YES) {
                    String sql = "UPDATE phieudatphong SET tinhtrang = ?\n"
                            + "WHERE maphieudat = ?";
                    PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
                    p.setString(1, CANCEL);
                    p.setString(2, lblMaPhieuDat.getText());
                    int result = jdbcConfig.ExecuteUpdateQuery(p);
                    if (result == 1) {
                        setAlertInfo("Thông báo", "Hủy đặt phòng thành công",
                                "Mã phiếu : " + lblMaPhieuDat.getText()
                                + "\nTên khách hàng : " + lblTenKhachHang.getText());
                        this.setTablePhieuDatPhong();
                    }

                }
            }
        }

    }

    @FXML
    public void luuThayDoi(ActionEvent e) throws SQLException {
        if (!lblMaPhieuDat.getText().equals("------")) {
            if (checkThongTinDatPhong()) {
                String sql = "UPDATE phieudatphong\n"
                        + "SET ngayden = ?,\n"
                        + "	ngaydi = ?,\n"
                        + "	sotiendatcoc = ?,\n"
                        + "	songuoi = ?\n"
                        + "WHERE maphieudat = ?";

                PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
                p.setString(1, getNgayDen());
                p.setString(2, getNgayDi());
                p.setDouble(3, Double.valueOf(txtDatCoc.getText()));
                p.setInt(4, Integer.valueOf(txtSoNguoi.getText()));
                p.setString(5, lblMaPhieuDat.getText());
                int i = jdbcConfig.ExecuteUpdateQuery(p);
                insertChiTietDatPhong(getMaPhieuDat());
                if (i == 1) {
                    setTablePhieuDatPhong();
                    setAlertInfo("Thông báo", "Cập nhật thành công",
                            "Mã phiếu đặt : " + lblMaPhieuDat.getText());
                    p.close();
                }
            }
        }
    }

    @FXML
    private void nhanPhong(ActionEvent e) throws SQLException {
        Date now = new Date();

        SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy");
        String current_time = timeFormat.format(now);
        String ngayden;

        if (lblMaPhieuDat.getText().equals("------")) {

            setAlertInfo("Thông báo", "Nhận phòng không thành công",
                    "Vui lòng chọn 1 phiếu đặt phòng");
        } else {
            ngayden = timeFormat.format(tblPhieuDatDS
                    .getSelectionModel().getSelectedItem().getNgayDen());
            if (!ngayden.equals(current_time)) {
                lblMaPhieuDat.setText("------");
                setAlertInfo("Thông báo", "Chưa đến ngày nhận phòng hoặc phiếu đã hết hạn",
                        "Vui lòng chọn phiếu khác");
            } else {
                if (!getTingTrang().equals(WAITTING)) {
                    setAlertInfo("Thông báo", "Nhận phòng không thành công",
                            "Chỉ nhận những phiếu WAITTING");
                } else {
                    jdbcConfig.connection.setAutoCommit(false);
                    String sql = "UPDATE phieudatphong SET "
                            + "tinhtrang = ?,\n"
                            + "songuoi = ?, \n"
                            + "ngayden = ?, \n"
                            + "ngaydi = ?,\n"
                            + "makhachhang = ?\n"
                            + "WHERE maphieudat = ?  ";
                    PreparedStatement pUpdatePhong = jdbcConfig.connection.prepareStatement(sql);
                    pUpdatePhong.setString(1, BUSY);
                    pUpdatePhong.setString(2, txtSoNguoi.getText());
                    pUpdatePhong.setString(3, getNgayDen());
                    pUpdatePhong.setString(4, getNgayDi());
                    pUpdatePhong.setString(5, lblMaKhachHang.getText());
                    pUpdatePhong.setString(6, getMaPhieuDat());

                    int i = jdbcConfig.ExecuteUpdateQuery(pUpdatePhong);
                    if (i == 1) {
                        String idPhieuThue = util.RandomId.createNewID("PT");
                        sql = "INSERT INTO phieuthuephong("
                                + "maphieuthue, maphieudat, username) "
                                + "VALUES (?,?,?)";
                        PreparedStatement pInserPhieuThue = jdbcConfig.connection.prepareStatement(sql);
                        pInserPhieuThue.setString(1, idPhieuThue);
                        pInserPhieuThue.setString(2, getMaPhieuDat());
                        pInserPhieuThue.setString(3, user);

                        int u = jdbcConfig.ExecuteUpdateQuery(pInserPhieuThue);
                        jdbcConfig.connection.commit();
                        if (u == 1) {
                            setTablePhieuDatPhong();

                            openFormPhieuThuePhong(idPhieuThue);
                            pInserPhieuThue.close();
                            pUpdatePhong.close();
                        }

                    }
                }
            }
        }
    }

    private void openFormPhieuThuePhong(String idPhieuThue) {
        try {
            jdbcConfig.Disconnect();
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/restaurant/manager/views/PhieuThuePhongFXML.fxml"));// set resource file FXML form
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            PhieuThuePhongController ptp = loader.getController();//form 2

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);//stage lock form children
            stage.setTitle("Quản lý phòng thuê");
            stage.setScene(scene);
            stage.show();
            ptp.sendIdPhieuThue(idPhieuThue);

        } catch (IOException ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setDefaltDpk() {
        dpkNgayDen.setValue(LocalDate.now());
        dpkNgayDi.setValue(LocalDate.now());
        lblNgayDen.setText(LocalDate.now().toString());
        lblNgayDi.setText(LocalDate.now().toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jdbcConfig.Connect(); // connect database
        try {
            setDefaltDpk();
            btnTimPhong.setOnAction(e -> {
                tblPhongDat.getItems().clear();//clear danh sach phong dat
                if (this.checkDatepicker()) {
                    if (this.checkInputText()) {
                        setTablePhong();
                    }
                }
            });
            this.setTablePhieuDatPhong();
            this.getTenKhachHang();
            this.eventChanged();

        } catch (SQLException ex) {
            Logger.getLogger(PhieuDatPhongController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

}
