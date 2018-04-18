/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import config.jdbcConfig;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import restaurant.manager.models.DichVuSuDung;
import restaurant.manager.models.HoaDon;
import restaurant.manager.models.PhongSuDung;

public class HoaDonController implements Initializable {

    @FXML
    private JFXTextField txtTiemKiem;
    @FXML
    private TableView<HoaDon> tblPhieuThue;
    @FXML
    private TableColumn<HoaDon, String> tblColMaPhieuThue;
    @FXML
    private TableColumn<HoaDon, String> tblColTenKH;
    @FXML
    private Label lblMaHoaDon;
    @FXML
    private Label lblTienDatCoc;
    @FXML
    private Label lblTienPhong;
    @FXML
    private Label lblTienDichVu;
    @FXML
    private JFXTextField txtNgayThanhToan;
    @FXML
    private JFXButton btnTinhTien;
    @FXML
    private JFXButton btnThoat;
    @FXML
    private Label lblMaKH;
    @FXML
    private Label lblTenKH;
    @FXML
    private Label lblDiaChi;
    @FXML
    private Label lblCmnd;
    @FXML
    private Label lblCoQuan;
    @FXML
    private Label lblSdt;
    @FXML
    private Label lblEmail;
    @FXML
    private TableColumn<?, ?> tblColMaPhong;
    @FXML
    private TableColumn<?, ?> tblColNgayDen;
    @FXML
    private TableColumn<?, ?> tblColSoNgayO;

    @FXML
    private TableColumn<?, ?> tblColSoLuong;
    @FXML
    private TableColumn<HoaDon, String> tblColTenNV;
    @FXML
    private Label lblGioiTinh;
    @FXML
    private TableView<DichVuSuDung> tblDichVuSD;
    @FXML
    private TableColumn<?, ?> tblColTenDV;
    @FXML
    private TableColumn<?, ?> tblColNgaySD;
    @FXML
    private TableColumn<?, ?> tblColDonVi;
    @FXML
    private TableColumn<?, ?> tblColThanhTien;
    @FXML
    private TableColumn<?, ?> tblColGiaDV;
    @FXML
    private TableColumn<?, ?> tblColThanhTienDV;
    @FXML
    private TableView<?> tblPhongSD;
    @FXML
    private TableColumn<?, ?> tblColMaPhongSD;
    @FXML
    private TableColumn<?, ?> tblColGiaSD;

    private ObservableList<HoaDon> listMaPhieuThue = null;
    private ObservableList<DichVuSuDung> listDichVuSuDung = null;
    private ObservableList<PhongSuDung> listPhongSudung = null;
    private final String FINISH = "finish";
    private final String username = "admin";
    private int tongTienDichVu = 0;
    private int tongTienPhong = 0;
    private int tienThanhToan = 0;
    @FXML
    private Label lblTongTien;
    @FXML
    private Label lblTienThanhToan;

    private ObservableList<HoaDon> getMaPhieuThue() {
        try {
            listMaPhieuThue = FXCollections.observableArrayList();
            String sql = "SELECT pt.maphieuthue, kh.tenkhachhang, nv.tennhanvien\n"
                    + "FROM phieuthuephong as pt, hethong as ht, nhanvien as nv,"
                    + " phieudatphong as pd, khachhang as kh\n"
                    + "WHERE pt.username = ht.username AND ht.manhanvien = nv.manhanvien \n"
                    + "AND pd.maphieudat = pt.maphieudat AND pd.tinhtrang = 'busy' AND kh.makhachhang = pd.makhachhang";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            ResultSet r = jdbcConfig.ExecuteQuery(p);
            while (r.next()) {
                listMaPhieuThue.add(new HoaDon(r.getString(1), r.getString(2), r.getString(3)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listMaPhieuThue;
    }

    private void setTablePhieuThue() {
        Map<TableColumn, String> mapCol = new HashMap<>();
        mapCol.put(tblColMaPhieuThue, "maPhieuThue");
        mapCol.put(tblColTenKH, "tenKH");
        mapCol.put(tblColTenNV, "tenNV");
        jdbcConfig.setTableView(tblPhieuThue, mapCol, getMaPhieuThue());
    }

    private void getKHByIdPhieuThue(String idPhieuDat) {
        try {
            String sql = "SELECT kh.*\n"
                    + "FROM phieuthuephong as pt, phieudatphong as pd, khachhang as kh\n"
                    + "WHERE pd.maphieudat = pt.maphieudat AND pt.maphieuthue = ?\n"
                    + "AND pd.tinhtrang = 'busy' AND kh.makhachhang = pd.makhachhang";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, idPhieuDat);
            ResultSet r = jdbcConfig.ExecuteQuery(p);
            while (r.next()) {
                lblMaKH.setText(r.getString(1));
                lblTenKH.setText(r.getString(2));
                lblGioiTinh.setText(r.getString(3));
                lblCmnd.setText(r.getString(4));
                lblDiaChi.setText(r.getString(5));
                lblCoQuan.setText(r.getString(6));
                lblSdt.setText(r.getString(7));
                lblEmail.setText(r.getString(8));
            }

        } catch (SQLException ex) {
            Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ObservableList<PhongSuDung> getPhongSuDungByIdPhieuThue(String id) {
        try {
            String sql = "SELECT p.maphong, lp.gia, pd.ngayden, ngayo = datediff(day, cast(pd.ngayden as date) , cast( GETDATE() as date)), \n"
                    + "datediff(day, cast(pd.ngayden as date) , cast( GETDATE() as date)) * lp.gia\n"
                    + "FROM phong as p, phieudatphong as pd, phieuthuephong as pt, chitietdatphong as ct, loaiphong as lp\n"
                    + "WHERE p.maphong = ct.maphong AND ct.maphieudat = pd.maphieudat AND pt.maphieudat = pd.maphieudat\n"
                    + "AND pd.tinhtrang = 'busy' AND lp.maloai = p.maloai AND pt.maphieuthue = ?";
            listPhongSudung = FXCollections.observableArrayList();
            try (PreparedStatement p = jdbcConfig.connection.prepareStatement(sql)) {
                p.setString(1, id);
                ResultSet r = jdbcConfig.ExecuteQuery(p);
                while (r.next()) {
                    listPhongSudung.add(new PhongSuDung(r.getString(1),
                            r.getDouble(2), r.getString(3), r.getInt(4),
                            r.getDouble(5)));
                    tongTienPhong += r.getDouble(5);
                }
                p.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPhongSudung;
    }

    private void getTablePhongSDById(String id) {
        Map<TableColumn, String> mapCol = new HashMap<>();
        mapCol.put(tblColMaPhongSD, "maPhong");
        mapCol.put(tblColGiaSD, "giaPhong");
        mapCol.put(tblColNgayDen, "ngayDen");
        mapCol.put(tblColSoNgayO, "soNgaySuDung");
        mapCol.put(tblColThanhTien, "thanhTien");
        listPhongSudung = getPhongSuDungByIdPhieuThue(id);
        jdbcConfig.setTableView(tblPhongSD, mapCol, listPhongSudung);
    }

    private ObservableList<DichVuSuDung> getDVSuDungByIdPhieuThue(String id) {
        try {
            String sql = "SELECT tendichvu, maphong, ct.ngay, ct.soluong ,dv.donvitinh, gia, ct.soluong*dv.gia\n"
                    + "FROM phieuthuephong as pt,chitietthuephong as ct, phieudatphong as pd, dichvu as dv\n"
                    + "WHERE pd.maphieudat = pt.maphieudat AND pt.maphieuthue = ?\n"
                    + "AND pd.tinhtrang = 'busy' AND ct.maphieuthue = pt.maphieuthue AND dv.madichvu = ct.madichvu";
            try (PreparedStatement p = jdbcConfig.connection.prepareStatement(sql)) {
                listDichVuSuDung = FXCollections.observableArrayList();
                p.setString(1, id);
                ResultSet r = jdbcConfig.ExecuteQuery(p);
                while (r.next()) {
                    listDichVuSuDung.add(new DichVuSuDung(r.getString(1),
                            r.getString(2), r.getString(3), r.getInt(4),
                            r.getString(5), r.getDouble(6), r.getDouble(7)));
                    tongTienDichVu += r.getDouble(7);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listDichVuSuDung;
    }

    private void getTableDichVuByIdPhieuThue(String id) {
        Map<TableColumn, String> mapCol = new HashMap<>();
        mapCol.put(tblColTenDV, "tenDichVu");
        mapCol.put(tblColMaPhong, "maPhong");
        mapCol.put(tblColNgaySD, "ngaySuDung");
        mapCol.put(tblColSoLuong, "soLuong");
        mapCol.put(tblColDonVi, "donViTinh");
        mapCol.put(tblColGiaDV, "gia");
        mapCol.put(tblColThanhTienDV, "thanhTien");
        listDichVuSuDung = getDVSuDungByIdPhieuThue(id);
        jdbcConfig.setTableView(tblDichVuSD, mapCol, listDichVuSuDung);

    }

    private void getTienCoc(String id) {
        try {
            String sql = "SELECT pd.sotiendatcoc  FROM phieudatphong as pd, phieuthuephong as pt\n"
                    + "WHERE pt.maphieuthue = ? AND pd.maphieudat = pt.maphieudat";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, id);
            ResultSet r = jdbcConfig.ExecuteQuery(p);
            while (r.next()) {
                lblTienDatCoc.setText(r.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tinhTien() {
        try {
            if (tblPhieuThue.getSelectionModel()
                    .getSelectedItem() != null) {
                jdbcConfig.connection.setAutoCommit(false);
                String idMaPhieuThue = tblPhieuThue.getSelectionModel()
                        .getSelectedItem().getMaPhieuThue();
                String sql = "INSERT INTO hoadon(mahoadon, ngaythanhtoan,"
                        + "tongtien,maphieuthue,makhachhang,username)\n"
                        + "VALUES(?,?,?,?,?,?)";
                PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
                p.setString(1, lblMaHoaDon.getText());
                p.setString(2, txtNgayThanhToan.getText());
                p.setString(3, lblTienThanhToan.getText());
                p.setString(4, idMaPhieuThue);
                p.setString(5, lblMaKH.getText());
                p.setString(6, username);
                int i = jdbcConfig.ExecuteUpdateQuery(p);
                if (i == 1) {
                    String capNhatTrangThai = "UPDATE phieudatphong SET tinhtrang = ?\n"
                            + "FROM phieudatphong as pd, phieuthuephong as pt\n"
                            + "WHERE pt.maphieudat = pd.maphieudat AND pt.maphieuthue = ?";
                    PreparedStatement p2 = jdbcConfig.connection.prepareStatement(capNhatTrangThai);
                    p2.setString(1, FINISH);
                    p2.setString(2, idMaPhieuThue);
                    int j = jdbcConfig.ExecuteUpdateQuery(p2);
                    if (j == 1) {
                        p.close();
                        p2.close();
                        jdbcConfig.connection.commit();
                        util.AlertCustom.setAlertInfo("Thông báo",
                                "Lập hóa đơn thành công",
                                "Hóa đơn : " + lblMaHoaDon.getText()
                                + "\nKhách hàng : " + lblTenKH.getText()
                                + "\nTổng tiền : " + lblTienThanhToan.getText());
                        setDefaultValue();
                    }
                }
            } else {
                util.AlertCustom.setAlertInfo("Thông báo",
                        "Thanh toán không thành công",
                        "Vui lòng chọn phiếu để thanh toán");
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void clickShowItem(MouseEvent e) {
        tongTienDichVu = 0;
        tongTienPhong = 0;

        if (tblPhieuThue.getSelectionModel()
                .getSelectedItem() != null) {
            if (!tblPhieuThue.getItems().isEmpty()) {
                String idMaPhieuThue = tblPhieuThue.getSelectionModel()
                        .getSelectedItem().getMaPhieuThue();
                getTableDichVuByIdPhieuThue(idMaPhieuThue);
                getTablePhongSDById(idMaPhieuThue);
                getKHByIdPhieuThue(idMaPhieuThue);
                getTienCoc(idMaPhieuThue);
                lblTienPhong.setText(String.valueOf(tongTienPhong));
                lblTienDichVu.setText(String.valueOf(tongTienDichVu));
                tienThanhToan = tongTienPhong + tongTienDichVu
                        - Integer.valueOf(lblTienDatCoc.getText());
                lblTongTien.setText(String.valueOf(tongTienPhong + tongTienDichVu));
                lblTienThanhToan.setText(String.valueOf(tienThanhToan));
            }
        }

    }

    private void setDefaultValue() {
        txtNgayThanhToan.setEditable(false);
        setTablePhieuThue();
        lblMaHoaDon.setText(util.RandomId.createNewID("HD"));
        txtNgayThanhToan.setText(util.CurrentTime.getCurrentTime());
        tblPhongSD.getItems().clear();
        tblDichVuSD.getItems().clear();
        lblTienDatCoc.setText("------");
        lblTienDichVu.setText("------");
        lblTienPhong.setText("------");
        lblTongTien.setText("------");
        lblTienThanhToan.setText("------");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jdbcConfig.Connect();
        setDefaultValue();
        btnThoat.setOnAction((e) -> {
            Optional<ButtonType> r = util.AlertCustom.setAlertConf(
                    "Bấm Yes để thoát", "Bạn có muốn thoát không");
            if (r.get() == ButtonType.YES) {
                jdbcConfig.Disconnect();
                Platform.exit();
            }
        });
        btnTinhTien.setOnAction((e) -> {
            Optional<ButtonType> r = util.AlertCustom.setAlertConf(
                    "Thao tác này không thể hoàn tác\n"
                    + "Khách hàng : " + lblTenKH.getText(),
                    "Bạn có muốn tính tiền không");
            if (r.get() == ButtonType.YES) {
                tinhTien();
            }
        });

    }
}
