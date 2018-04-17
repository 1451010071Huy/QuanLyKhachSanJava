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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import restaurant.manager.models.DichVu;
import restaurant.manager.models.DichVuSuDung;
import restaurant.manager.models.KhachHang;
import restaurant.manager.models.PhieuThuePhong;
import static util.AlertCustom.setAlertConf;

/**
 * FXML Controller class
 *
 * @author Luxury
 */
public class PhieuThuePhongController implements Initializable {

    @FXML
    private TableView<DichVuSuDung> tblDichVuSD;
    @FXML
    private TableColumn<DichVuSuDung, String> tblColTenDVSD;
    @FXML
    private TableColumn<DichVuSuDung, String> tblColNgayMuaDV;
    @FXML
    private TableColumn<DichVuSuDung, Integer> tblColSoLSD;
    @FXML
    private ComboBox<String> cbbMaPhieuThue;
    @FXML
    private ComboBox<String> cbbMaPhong;
    @FXML
    private Label lblGiaDichVu;
    @FXML
    private TextField txtSoLuong;
    @FXML
    private Label lblTenDichVu;
    @FXML
    private TextField txtTimKiemDV;
    @FXML
    private TableView<DichVu> tblDichVu;
    @FXML
    private TableColumn<DichVu, String> tblColMaDV;
    @FXML
    private TableColumn<DichVu, String> tblColTenDV;
    @FXML
    private TableColumn<DichVu, Integer> tblColGia;
    @FXML
    private TableColumn<DichVu, String> tblColDonVi;
    @FXML
    private Label lblTongThanhTien;

    ObservableList<DichVu> listDichVu = null;
    ObservableList<KhachHang> listKhachHang = null;
    ObservableList<PhieuThuePhong> listPhieuThuePhong = null;
    @FXML
    private Label lblMaKH;
    @FXML
    private Label lblTenKH;
    @FXML
    private Label lblCmnd;
    @FXML
    private Label lblDiaChi;
    @FXML
    private Label lblCoQuan;
    @FXML
    private Label lblSdt;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblGioiTinh;
    @FXML
    private TextField txtNgaySDDichVu;

    ObservableList<DichVuSuDung> listDichVuSuDung = null;
    @FXML
    private TableColumn<DichVuSuDung, Integer> tblColDonViTinhSD;
    @FXML
    private Button btnThemDV;
    @FXML
    private TableColumn<DichVuSuDung, Double> tblColThanhTien;
    @FXML
    private TableColumn<DichVuSuDung, Double> tblColDonGiaDV;
    @FXML
    private TableColumn<DichVuSuDung, Button> tblColXoaDV;

    private ObservableList<DichVu> getDichVu() {
        try {
            String sql = "SELECT * FROM dichvu";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            listDichVu = FXCollections.observableArrayList();
            ResultSet r = jdbcConfig.ExecuteQuery(p);
            while (r.next()) {
                listDichVu.add(new DichVu(r.getString(1), r.getString(2), r.getInt(3), r.getString(4)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuThuePhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listDichVu;
    }

    private void getTableDichVu() {
        Map<TableColumn, String> mapCol = new HashMap<>();
        mapCol.put(tblColMaDV, "maDichVu");
        mapCol.put(tblColTenDV, "tenDichVu");
        mapCol.put(tblColGia, "gia");
        mapCol.put(tblColDonVi, "donViTinh");
        jdbcConfig.setTableView(tblDichVu, mapCol, getDichVu());
    }

    private void getPhieuThuePhong() {
        try {
            String s = "SELECT maphieuthue "
                    + "FROM phieuthuephong as pt, "
                    + "phieudatphong as pd\n"
                    + "WHERE pd.maphieudat = pt.maphieudat "
                    + "AND pd.tinhtrang = 'busy'";
            PreparedStatement p = jdbcConfig.connection.prepareCall(s);
            listPhieuThuePhong = FXCollections.observableArrayList();

            ResultSet r = jdbcConfig.ExecuteQuery(p);
            while (r.next()) {
                cbbMaPhieuThue.getItems().add(r.getString(1));
            }
            setKhByMaPhieuThue();
            p.close();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuThuePhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ObservableList<KhachHang> getKhachHangByIdPT() {
        return null;
    }

    @FXML
    private void clickItem(MouseEvent e) {
        lblTenDichVu.setText(tblDichVu.getSelectionModel()
                .getSelectedItem().getTenDichVu());
        lblGiaDichVu.setText(String.valueOf(tblDichVu.getSelectionModel()
                .getSelectedItem().getGia()));
        txtNgaySDDichVu.setText(util.CurrentTime.getCurrentTime());

    }

    private String getMaPhieuThue() {
        return cbbMaPhieuThue.getValue();
    }

    private String getMaPhong() {
        return cbbMaPhong.getValue();
    }

    private String getMaDichVu() {
        return tblDichVu.getSelectionModel()
                .getSelectedItem().getMaDichVu();
    }

    private void setKhByMaPhieuThue() throws SQLException {
        String sql = "SELECT kh.* FROM phieuthuephong as pt, "
                + "phieudatphong as pd,khachhang as kh\n"
                + "WHERE pt.maphieudat = pd.maphieudat "
                + "AND pd.makhachhang = kh.makhachhang "
                + "AND pt.maphieuthue = ?";
        try (PreparedStatement p = jdbcConfig.connection.prepareStatement(sql)) {
            p.setString(1, getMaPhieuThue());
            ResultSet r = p.executeQuery();
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
            getPhongByMaPhieuThue();
        }

    }

    @FXML
    private void clickComboBoxShowItemsKH(ActionEvent e) {
        try {
            getTongThanhTien();
            setKhByMaPhieuThue();
        } catch (SQLException ex) {
            Logger.getLogger(PhieuThuePhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void clickComboBoxShowItemsDV(ActionEvent e) {
        getTongThanhTien();
        getTableSuDungDV();
    }

    private void getPhongByMaPhieuThue() {
        cbbMaPhong.getItems().clear();
        try {
            String sql = "SELECT maphong FROM  phieudatphong as pd, "
                    + "chitietdatphong as ct, phieuthuephong as pt\n"
                    + "WHERE ct.maphieudat = pd.maphieudat "
                    + "AND pd.tinhtrang = 'busy' \n"
                    + "AND pt.maphieudat = pd.maphieudat "
                    + "AND pt.maphieuthue = ?";

            try (PreparedStatement p = jdbcConfig.connection.prepareStatement(sql)) {
                p.setString(1, getMaPhieuThue());
                ResultSet r = jdbcConfig.ExecuteQuery(p);
                while (r.next()) {
                    cbbMaPhong.getItems().add(r.getString(1));
                }
                cbbMaPhong.getSelectionModel().select(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuThuePhongController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ObservableList<DichVuSuDung> getDichVuByMaPhieuThue() {
        try {
            String sql = "SELECT tendichvu, ngay, soluong, donvitinh, gia, gia*soluong, dichvu.madichvu\n"
                    + "  FROM chitietthuephong, dichvu\n"
                    + "  WHERE maphong = ? AND maphieuthue = ? \n"
                    + "  AND chitietthuephong.madichvu = dichvu.madichvu";

            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            listDichVuSuDung = FXCollections.observableArrayList();
            p.setString(1, getMaPhong());
            p.setString(2, getMaPhieuThue());

            ResultSet r = jdbcConfig.ExecuteQuery(p);
            while (r.next()) {
                listDichVuSuDung.add(new DichVuSuDung(r.getString(1),
                        r.getString(2), r.getInt(3), r.getString(4),
                        r.getDouble(5), r.getDouble(6), r.getString(7)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuThuePhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listDichVuSuDung;
    }

    public void getTableSuDungDV() {
        Map<TableColumn, String> mapCol = new HashMap<>();
        mapCol.put(tblColTenDVSD, "tenDichVu");
        mapCol.put(tblColNgayMuaDV, "ngaySuDung");
        mapCol.put(tblColSoLSD, "soLuong");
        mapCol.put(tblColDonViTinhSD, "donViTinh");
        mapCol.put(tblColDonGiaDV, "gia");
        mapCol.put(tblColThanhTien, "thanhTien");
        mapCol.put(tblColXoaDV, "btnXoaDV");
        listDichVuSuDung = getDichVuByMaPhieuThue();
        listDichVuSuDung.forEach(value -> {
            value.getBtnXoaDV().setOnAction(e -> {
                Optional<ButtonType> result = setAlertConf(
                        "Tên dịch vụ : " + value.getTenDichVu()
                        + "\nMã phòng : " + getMaPhong(),
                        "Bạn có muốn xóa không"
                );
                if (result.get() == ButtonType.YES) {
                    xoaDVByMaDV(value.getTenDichVu(),
                            value.getMaDichVu(), value.getNgaySuDung());
                    getTableSuDungDV();
                    getTongThanhTien();
                }

            });
        });

        jdbcConfig.setTableView(tblDichVuSD, mapCol, listDichVuSuDung);
    }

    public void xoaDVByMaDV(String tenDV, String maDV, String ngaySD) {
        try {
            String sql = "DELETE chitietthuephong\n"
                    + "WHERE madichvu = ? AND ngay = ?";
            try (PreparedStatement p = jdbcConfig.connection.prepareStatement(sql)) {
                p.setString(1, maDV);
                p.setString(2, ngaySD);

                int i = jdbcConfig.ExecuteUpdateQuery(p);
                if (i == 1) {
                    util.AlertCustom.setAlertInfo("Thông báo",
                            "Xóa thành công",
                            "Dịch vụ : " + tenDV
                            + "\nMã phòng : " + getMaPhong());
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(PhieuThuePhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkSLDichVu() {
        if (txtSoLuong.getText().equals("")) {
            return false;
        } else {
            return Integer.valueOf(txtSoLuong.getText()) > 0;
        }
    }

    private boolean checkThongTin() {
        if (!checkSLDichVu()) {
            util.AlertCustom.setAlertInfo("Thông báo", "Thêm không thành công",
                    "Số lượng không hợp lệ");
            return false;
        } else if (cbbMaPhieuThue.getValue() == null) {
            util.AlertCustom.setAlertInfo("Thông báo", "Thêm không thành công",
                    "Vui lòng chọn mã phiếu thuê");
            return false;
        } else if (cbbMaPhong.getValue() == null) {
            util.AlertCustom.setAlertInfo("Thông báo", "Thêm không thành công",
                    "Vui lòng chọn mã phòng sử dụng DV");
            return false;
        } else if (lblGiaDichVu.getText().equals("--------")
                || lblTenDichVu.getText().equals("--------")) {
            util.AlertCustom.setAlertInfo("Thông báo", "Thêm không thành công",
                    "Vui lòng chọn dịch vụ sử dụng");
            return false;
        }
        return true;
    }

    private void themDichVu() {
        try {
            if (checkThongTin()) {
                String sql = "  INSERT INTO chitietthuephong(maphieuthue, maphong, ngay, madichvu, soluong)\n"
                        + "  VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement p = jdbcConfig.connection.prepareStatement(sql)) {
                    p.setString(1, getMaPhieuThue());
                    p.setString(2, getMaPhong());
                    p.setString(3, txtNgaySDDichVu.getText());
                    p.setString(4, getMaDichVu());
                    p.setString(5, txtSoLuong.getText());
                    int i = jdbcConfig.ExecuteUpdateQuery(p);
                    if (i == 1) {
                        util.AlertCustom.setAlertInfo("Thông báo",
                                "Thêm dịch vụ thành công", lblTenDichVu.getText()
                                + "\nSố lượng : " + txtSoLuong.getText());
                        getTableSuDungDV();
                        getTongThanhTien();
                    }

                }
            }

        } catch (SQLException ex) {
            util.AlertCustom.setAlertInfo("Thông báo", "Thêm không thành công",
                    "Đã xảy ra sự cố");
        }
    }

    private void getTongThanhTien() {
        double tongTien = 0;
        try {
            String sql = "SELECT SUM(gia*soluong)\n"
                    + "FROM chitietthuephong, dichvu\n"
                    + "WHERE maphong = ? AND maphieuthue =?\n"
                    + "AND chitietthuephong.madichvu = dichvu.madichvu";

            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            listDichVuSuDung = FXCollections.observableArrayList();
            p.setString(1, getMaPhong());
            p.setString(2, getMaPhieuThue());

            ResultSet r = jdbcConfig.ExecuteQuery(p);
            while (r.next()) {
                tongTien = r.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhieuThuePhongController.class.getName()).log(Level.SEVERE, null, ex);
        }

        lblTongThanhTien.setText(String.valueOf(tongTien));
    }

    public void sendIdPhieuThue(String idPhieuThue) {
        cbbMaPhieuThue.getSelectionModel().select(idPhieuThue);
    }

    private void setDefaultValue() {
        jdbcConfig.Connect();
        txtNgaySDDichVu.setEditable(false);
        txtNgaySDDichVu.setText(util.CurrentTime.getCurrentTime());
        getTongThanhTien();
        getTableDichVu();
        getPhieuThuePhong();
        getTableSuDungDV();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setDefaultValue();
        btnThemDV.setOnAction((e) -> {
            themDichVu();
        });

    }

}
