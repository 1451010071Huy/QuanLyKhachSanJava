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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    @FXML
    private TabPane tabIndex;
    @FXML
    private Tab tabIndex1;
    @FXML
    private Tab tabIndex2;

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

    private void setTablePhieuDatPhong() {
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

        listPhongDat = getChiTietDatPhong();
        listPhongDat.forEach(value -> {
            value.getXoaPhong().setOnAction(e -> {
                listPhongDat.remove(value);
            });
        });
        jdbcConfig.setTableView(tblPhongDat, mapCol, listPhongDat);
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
        chbFinnis.setOnAction(e -> {
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
                    "Tên khách hàng không được để trống",
                    "Vui lòng nhập khách hàng");
            return false;
        } else if ("".equals(txtSoNguoi.getText())) {
            setAlertInfo(
                    "Thông báo",
                    "Số lượng khách hàng không được để trống",
                    "Vui lòng nhập số lượng khách hàng");
            return false;
        } else if (checkDatepicker() == false) {
            return false;
        }
        return true;
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
        if ((tblPhongDat.getItems().isEmpty() && !checkDatepicker() && !checkInputText())) {
            setAlertInfo("Thông báo", "Đặt phòng không thành công",
                    "Xin vui lòng chọn phòng đặt");
            return false;
        } else {
            return true;
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
                        Optional<ButtonType> result = setAlertConf("Thông báo",
                                "Bạn có muốn xóa không");
                        if (result.get() == ButtonType.OK) {
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
    public void clickItemPhieuDat(MouseEvent event) throws SQLException {
        if (event.getClickCount() == 1) //Checking double click
        {
            tblPhieuDatDS.getSelectionModel().selectedIndexProperty().addListener((num) -> selectItem());
        }
    }

    public void selectItem() {
        try {
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
        } catch (SQLException ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void datPhong(ActionEvent a) throws SQLException {
        PreparedStatement pInsertPDP = null;
        PreparedStatement pInsertPhong = null;
        try {
            if (checkThongTinDatPhong()) {
                jdbcConfig.connection.setAutoCommit(false);
                String id = util.RandomId.createNewID("PD");
                String ngayDen = dpkNgayDen.getValue().toString() + " 12:00:00";
                String ngayDi = dpkNgayDen.getValue().toString() + " 12:00:00";
                String sql = "INSERT INTO phieudatphong("
                        + "maphieudat, makhachhang,ngayden, ngaydi, "
                        + "sotiendatcoc, username, tinhtrang, songuoi)\n"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                pInsertPDP = jdbcConfig.connection.prepareStatement(sql);
                pInsertPDP.setString(1, id);
                pInsertPDP.setString(2, lblMaKhachHang.getText());
                pInsertPDP.setString(3, ngayDen);
                pInsertPDP.setString(4, ngayDi);
                pInsertPDP.setString(5, txtDatCoc.getText());
                pInsertPDP.setString(6, "admin");
                pInsertPDP.setString(7, "waitting");
                pInsertPDP.setString(8, txtSoNguoi.getText());

                int r = pInsertPDP.executeUpdate();
                int i = 0;
                if (r == 1) {
                    String query = "INSERT INTO chitietdatphong(maphieudat,maphong)\n"
                            + "VALUES (?,?)";
                    for (Phong item : tblPhongDat.getItems()) {
                        pInsertPhong = jdbcConfig.connection.prepareStatement(query);
                        pInsertPhong.setString(1, id);
                        pInsertPhong.setString(2, item.getMaPhong());
                        i = jdbcConfig.ExecuteUpdateQuery(pInsertPhong);
                    }
                    if (i == 1) {
                        util.AlertCustom.setAlertInfo("Thông báo",
                                "Đặt phòng thành công", "Mã phiếu : " + id
                                + "\nKhách hàng : " + lblTenKhachHang.getText()
                                + "\nNgày đặt : " + dpkNgayDen.getValue().toString());
                        tblPhongTrong.getItems().clear();// xóa tất cả danh sách phòng trống            
                        tabIndex.getSelectionModel().select(tabIndex2);//focus tab phiếu đặt phòng
                    }
                    jdbcConfig.connection.commit();
                    setTablePhieuDatPhong();
                }
            } else {
                setAlertInfo("Thông báo", "Bạn chưa chọn phòng đặt",
                        "Vui lòng chọn phòng để đặt");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (pInsertPDP != null) {
                pInsertPDP.close();
            }
            if (pInsertPhong != null) {
                pInsertPhong.close();
            }
        }
    }

    @FXML
    private void huyDatPhong(ActionEvent e) throws SQLException {
        if (!"------".equals(lblMaPhieuDat.getText())) {
            String sql = "UPDATE phieudatphong SET tinhtrang = 'cancel'\n"
                    + "WHERE maphieudat = ?";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, lblMaPhieuDat.getText());
            int result = jdbcConfig.ExecuteUpdateQuery(p);
            if (result == 1) {
                setAlertInfo("Thông báo", "Hủy đặt phòng thành công",
                        "Mã phiếu : " + lblMaPhieuDat.getText()
                        + "\nTên khách hàng : " + lblTenKhachHang.getText());
                this.setTablePhieuDatPhong();
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        jdbcConfig.Connect(); // connect database
        try {
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
