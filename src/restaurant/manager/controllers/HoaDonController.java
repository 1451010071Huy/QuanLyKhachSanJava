/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import config.jdbcConfig;
import java.awt.Color;
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

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import static util.FormatNumber.formatNumber;

public class HoaDonController implements Initializable {

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
    @FXML
    private Label lblTongTien;
    @FXML
    private Label lblTienThanhToan;
    @FXML
    private JFXTextField txtTimKiem;
    private ObservableList<HoaDon> listMaPhieuThue = null;
    private ObservableList<DichVuSuDung> listDichVuSuDung = null;
    private ObservableList<PhongSuDung> listPhongSudung = null;
    private final String FINISH = "finish";
    private final String username = "admin";
    private int tongTienDichVu = 0;
    private int tongTienPhong = 0;
    private int tienThanhToan = 0;
    private int tienCoc = 0;

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
                tienCoc = r.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int updateTrangThaiPhong(String idPhong, String trangThai) {
        int i = 0;
        try {
            String sql = "UPDATE phong SET trangthai = ?\n"
                    + "WHERE maphong = ?";

            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, trangThai);
            p.setString(2, idPhong);
            i = jdbcConfig.ExecuteUpdateQuery(p);
        } catch (SQLException ex) {
            Logger.getLogger(PhieuDatPhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
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
                p.setInt(3, tienThanhToan);
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
                        listPhongSudung.forEach((value)->{
                            updateTrangThaiPhong(value.getMaPhong(), "Phòng trống");
                        });
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
                lblTienDatCoc.setText(formatNumber(tienCoc));
                lblTienPhong.setText(formatNumber(tongTienPhong));
                lblTienDichVu.setText(formatNumber(tongTienDichVu));
                tienThanhToan = (tongTienPhong + tongTienDichVu) - tienCoc;
                lblTongTien.setText(formatNumber(tongTienPhong + tongTienDichVu));
                lblTienThanhToan.setText(String.format("%s VNĐ", formatNumber(tienThanhToan)));
            }
        }

    }

    private String getTenNhanVien() {
        return tblPhieuThue.getSelectionModel()
                .getSelectedItem().getTenNV();
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

    public void inHoaDon() {
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new BillPrintable(), getPageFormat(pj));
        try {
            pj.setJobName("hoadon");// ten cua hoa don

            pj.print(); // in hoa don

        } catch (PrinterException ex) {
        }
    }

    //đổi cm -> ppi (1 cm = 0,393700787 inch)
    private double convertCmToPPI(double cm) {
        return toPPI(cm * 0.393600787);
    }

    //Hàm này để chuyển đổi ppi sang inch 
    private double toPPI(double inch) {
        return inch * 72d;
    }

    public PageFormat getPageFormat(PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double width = convertCmToPPI(8);      //chiều rộng của hóa đơn, mặc định là 72ppi cho mỗi inch
        double height = convertCmToPPI(18);
        paper.setSize(width, height);
        paper.setImageableArea(
                10,
                10,
                width,
                height - convertCmToPPI(1)
        );   //xác định kích thước vùng hiển thị hóa đơn là khoảng 
        //với x, y là lề trái và phải

        pf.setOrientation(PageFormat.PORTRAIT);//Xác định in theo chiều ngang hay dọc của hóa đơn
        pf.setPaper(paper);

        return pf;
    }

    public String getSpace(String string) {
        String space = "";
        String[] ar = string.split("");
        for (int i = 0; i < (22 - ar.length); i++) {
            space += " ";
        }
        return space;
    }

    public class BillPrintable implements Printable {

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                throws PrinterException {

            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {

                Graphics2D g2d = (Graphics2D) graphics;

                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

                int y = 20;
                int ySpacing10 = 10;
                int ySpacing13 = 13;
                int ySpacing15 = 15;
                int ySpacing18 = 18;
                g2d.setFont(new Font("Monospaced", Font.BOLD, 13));
                g2d.drawString("KHÁCH SẠN OU ", 12, y);
                y += ySpacing13;
                g2d.drawString("471 Nguyễn Kiệm-P.9-Q.PN ", 12, y);
                y += ySpacing13;
                g2d.drawString("ĐT : 028 3930 0210", 12, y);
                y += ySpacing10;
                g2d.drawString("____________________________________", 12, y);
                y += ySpacing15;
                g2d.setColor(Color.decode("#036C05"));
                g2d.setFont(new Font("Monospaced", Font.BOLD, 13));
                g2d.drawString("HÓA ĐƠN TÍNH TIỀN", 55, y);
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 11));
                g2d.setColor(Color.BLACK);
                y += ySpacing10;
                g2d.drawString(util.CurrentTime.getCurrentTime(), 59, y);
                y += ySpacing18;
                g2d.drawString("Nhân viên: " + getTenNhanVien(), 12, y);
                y += ySpacing10;
                g2d.drawString("Khách hàng: " + lblTenKH.getText(), 12, y);

                String ngayDen = "";

                for (PhongSuDung dv : listPhongSudung) {
                    ngayDen = dv.getNgayDen();
                }
                y += ySpacing10;
                g2d.drawString("Ngày đến: " + ngayDen, 12, y);
                y += ySpacing13;
                g2d.drawString("-------------------------------------", 10, y);
                y += ySpacing10;
                g2d.drawString("Tên dịch vụ(SL)       Giá(VNĐ)", 10, y);
                y += ySpacing10;
                g2d.drawString("-------------------------------------", 10, y);
                y += ySpacing13;
                for (DichVuSuDung dv : listDichVuSuDung) {
                    g2d.drawString(String.format(" %s(%s)%s%s",
                            dv.getTenDichVu(),
                            dv.getSoLuong(),
                            getSpace(dv.getTenDichVu() + "(1)"),
                            formatNumber((int) dv.getGia())), 10, y);
                    y += ySpacing10;
                }
                y += ySpacing10;
                g2d.drawString("-------------------------------------", 10, y);
                y += ySpacing10;
                g2d.drawString("Tên phòng             Giá(VNĐ)", 10, y);
                y += ySpacing10;
                g2d.drawString("-------------------------------------", 10, y);
                y += ySpacing10;
                for (PhongSuDung p : listPhongSudung) {
                    g2d.drawString(String.format(" %s%s%s", p.getMaPhong(),
                            getSpace(p.getMaPhong()),
                            formatNumber((int) p.getGiaPhong())), 10, y);
                    y += ySpacing10;
                }
                y += ySpacing10;
                g2d.drawString("=====================================", 10, y);
                y += ySpacing15;
                g2d.drawString("TIỀN CỌC     :         "
                        + lblTienDatCoc.getText(), 10, y);
                y += ySpacing13;
                g2d.drawString("TIỀN PHÒNG   :         "
                        + lblTienPhong.getText(), 10, y);
                y += ySpacing13;
                g2d.drawString("TIỀN DỊCH VỤ :         "
                        + lblTienDichVu.getText(), 10, y);
                y += ySpacing13;
                g2d.drawString("GIẢM GIÁ     :         " + "0", 10, y);
                y += ySpacing10;
                g2d.drawString("_____________________________________", 10, y);
                y += ySpacing18;
                g2d.setFont(new Font("Monospaced", Font.BOLD, 11));
                g2d.drawString("THANH TOÁN             "
                        + formatNumber(tienThanhToan), 10, y);
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 11));
                y += ySpacing18;
                g2d.drawString("*************************************", 10, y);
                y += ySpacing10;
                g2d.setColor(Color.decode("#036C05"));
                g2d.setFont(new Font("Monospaced", Font.BOLD, 11));
                g2d.drawString(" HÂN HẠNH ĐƯỢC PHỤC VỤ QUÝ KHÁCH ", 10, y);
                g2d.setFont(new Font("Monospaced", Font.PLAIN, 11));
                g2d.setColor(Color.BLACK);
                y += ySpacing10;
                g2d.drawString("*************************************", 10, y);
                y += ySpacing10;

                result = PAGE_EXISTS;
            }
            return result;
        }
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
            if (!getMaPhieuThue().equals("------")) {
                Optional<ButtonType> r = util.AlertCustom.setAlertConf(
                        "Bạn có muốn in hóa đơn không\n"
                        + "Khách hàng : " + lblTenKH.getText(),
                        "Bấm yes để in và thanh toán!");
                if (r.get() == ButtonType.YES) {
                    inHoaDon();
                    tinhTien();
                } else {
                    tinhTien();
                }
            } else {
                util.AlertCustom.setAlertInfo("Thông báo",
                        "Tính tiền không thành công",
                        "Vui lòng chọn phiếu thuê");
            }
        });
    }
}
