/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager;

import com.jfoenix.controls.JFXTextField;
import config.jdbcConfig;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import restaurant.manager.controllers.PhieuDatPhongController;
import restaurant.manager.controllers.PhieuThuePhongController;
import restaurant.manager.models.Phong;

/**
 *
 * @author Luxury
 */
public class MainController implements Initializable {

    Button btnPhong;
    @FXML
    private Text lblUser;
    @FXML
    private Pane paneDSPhong;
    @FXML
    private BorderPane boderPane;
    @FXML
    private VBox paneUser;
    @FXML
    private HBox hbxThuePhong;
    @FXML
    private HBox hbxHoaDon;
    @FXML
    private HBox hbxNhanVien;
    @FXML
    private HBox hbxKhachHang;
    @FXML
    private HBox hbxDichVu;
    @FXML
    private HBox hbxPhong;
    @FXML
    private HBox hbxBaoCao;
    @FXML
    private HBox hbxDatPhong;
    @FXML
    private HBox hbxQuanLyTaiKhoan;
    @FXML
    private JFXTextField txtTimKiem;
    @FXML
    private StackPane filterPT;
    @FXML
    private StackPane filterPD;
    @FXML
    private StackPane filterHP;
    @FXML
    private StackPane imgRefresh;
    @FXML
    private Label lblPhongT;
    @FXML
    private Label lblPhongDat;
    @FXML
    private Label lblHetPhong;
    @FXML
    private Label lblTongPhong;

    private final String WAITTING = "waitting";
    private final String PHONGTRONG = "Phòng Trống";
    private final String DADAT = "Đã Đặt";
    private final String HETPHONG = "Hết Phòng";
    private final String BUSY = "busy";
    private int countPhongTrong = 0;
    private int countPhongDat = 0;
    private int countHetPhong = 0;
    private int countTongPhong = 0;
    ObservableList<Phong> listPhong = FXCollections.observableArrayList();

    public void setUsername(String username) {
        this.lblUser.setText(username);
    }

    public ObservableList<Phong> getDSPhong() {
        countTongPhong = 0;
        try {
            paneDSPhong.getChildren().clear();
            listPhong.clear();
            String sql = "SELECT * FROM phong";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            ResultSet r = jdbcConfig.ExecuteQuery(p);

            while (r.next()) {
                listPhong.add(new Phong(r.getString(1), r.getString(2), r.getString(3)));
                countTongPhong++;
            }
            drawPhong(listPhong);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPhong;
    }

    public void setPermissionUsername(String username) {
        try {
            String sql = "SELECT chucvu FROM hethong as ht, nhanvien as nv\n"
                    + "WHERE ht.manhanvien = nv.manhanvien AND ht.username = ?";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, username);
            ResultSet r = jdbcConfig.ExecuteQuery(p);
            while (r.next()) {
                setPermistion(r.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void setPermistion(String permisstion) {
        switch (permisstion) {
            case "admin":
                break;
            case "Quản lý":
                hbxQuanLyTaiKhoan.setDisable(true);
                break;
            case "Nhân viên":
                hbxBaoCao.setDisable(true);
                hbxNhanVien.setDisable(true);
                hbxQuanLyTaiKhoan.setDisable(true);
                hbxPhong.setDisable(true);
                hbxDichVu.setDisable(true);
                break;
            default:
                break;
        }
    }

    public void openForm(String resource, String titleStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource(resource));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);//stage lock form children
            stage.setTitle(titleStage);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void hboxThuePhongClick(MouseEvent event) {
        String resource = "views/PhieuThuePhongFXML.fxml";
        String titleScene = "Thuê phòng";
        openForm(resource, titleScene);
    }

    @FXML
    public void hboxDatPhongClick(MouseEvent event) {
        String resource = "views/PhieuDatPhongFXML.fxml";
        String titleScene = "Quản lý đặt phòng";
        openForm(resource, titleScene);

    }

    @FXML
    public void hboxHoaDonClick(MouseEvent event) {
        String resource = "views/HoaDonFXML.fxml";
        String titleScene = "Quản lý hóa đơn";
        openForm(resource, titleScene);
    }

    @FXML
    public void hboxNhanVienClick(MouseEvent event) {
        String resource = "views/NhanVienFXML.fxml";
        String titleScene = "Quản lý nhân viên";
        openForm(resource, titleScene);
    }

    @FXML
    public void hboxKhachHangClick(MouseEvent event) {
        String resource = "views/KhachHangFXML.fxml";
        String titleScene = "Quản lý khách hàng";
        openForm(resource, titleScene);
    }

    @FXML
    public void hboxDichVuClick(MouseEvent event) {
        String resource = "views/DichVuFXML.fxml";
        String titleScene = "Quản lý dịch vụ";
        openForm(resource, titleScene);
    }

    @FXML
    public void hboxPhongClick(MouseEvent event) {
        String resource = "views/PhongFXML.fxml";
        String titleScene = "Quản lý phòng";
        openForm(resource, titleScene);
    }

    @FXML
    public void hboxBaoCaoClick(MouseEvent event) {
        String resource = "views/BarChartFXML.fxml";
        String titleScene = "Báo cáo thống kê";
        openForm(resource, titleScene);
    }

    @FXML
    public void hboxQLTaiKhoanClick(MouseEvent event) {
        String resource = "views/HeThongFXML.fxml";
        String titleScene = "Quản lý tài khoản";
        openForm(resource, titleScene);
    }

    @FXML
    public void hboxDangXuat(MouseEvent event) {
        try {
            Optional<ButtonType> result = util.AlertCustom
                    .setAlertConf("Bấm yes để đăng xuất",
                            "Bạn có muốn đăng xuất không");
            if (result.get() == ButtonType.YES) {
                FXMLLoader loader = new FXMLLoader(getClass()
                        .getResource("views/LoginFXML.fxml"));
                Parent root;
                root = (Parent) loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("Đăng nhập");
                stage.setScene(scene);
                stage.show();

                //close form main
                stage = (Stage) lblUser.getScene().getWindow();
                stage.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void timKiemPhong() {
        paneDSPhong.getChildren().clear();
        listPhong.clear();
        try {
            String sql = "SELECT * FROM phong\n"
                    + "WHERE maphong LIKE ? OR maloai LIKE ? OR trangthai LIKE ?";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, "%" + txtTimKiem.getText().trim() + "%");
            p.setString(2, "%" + txtTimKiem.getText().trim() + "%");
            p.setString(3, "%" + txtTimKiem.getText().trim() + "%");
            ResultSet r = jdbcConfig.ExecuteQuery(p);

            while (r.next()) {
                listPhong.add(new Phong(r.getString(1), r.getString(2), r.getString(3)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Label lblStatus = new Label();
        lblStatus.setText("");
        lblStatus.setFont(Font.font(15));
        lblStatus.layoutXProperty().bind(paneDSPhong.widthProperty()
                .subtract(lblStatus.widthProperty()).divide(2));//Cho nó ra giữa theo chiều rộng pane
        lblStatus.layoutYProperty().bind(paneDSPhong.heightProperty()
                .subtract(lblStatus.heightProperty()).divide(2));//Cho nó ra giữa theo chiều cao pane
        if (listPhong.isEmpty()) {
            lblStatus.setTextFill(Color.WHITE);
            lblStatus.setText("   Không tìm thấy phòng nào.\n"
                    + "Vui lòng sử dụng từ khóa khác!");
        } else {
            drawPhong(listPhong);
            lblStatus.setText("");
        }
        paneDSPhong.getChildren().add(lblStatus);
    }

    public void drawPhong(ObservableList<Phong> listP) {
        double i = 1;
        int x = 20;
        int y = 20;
        countHetPhong = countPhongDat = countPhongTrong = 0;
        for (Phong p : listP) {
            final int kichThuoc = 80;
            btnPhong = new Button();
            btnPhong.setMinWidth(kichThuoc);
            btnPhong.setMinHeight(kichThuoc);
            btnPhong.setText(String.format("%s\n%s\n%s",
                    p.getMaPhong(), p.getMaLoai(), p.getTrangThai()));
            btnPhong.setLayoutX(x);
            btnPhong.setLayoutY(y);
            x += 110;
            i++;
            if (i == 8) {
                y += kichThuoc + 20;
                x = 20;
                i = 1;

            }
            switch (p.getTrangThai()) {
                case DADAT:
                    btnPhong.getStyleClass().add("room-waitting");
                    countPhongDat++;
                    break;
                case HETPHONG:
                    countHetPhong++;
                    btnPhong.getStyleClass().add("room-busy");
                    break;
                default:
                    countPhongTrong++;
                    btnPhong.getStyleClass().add("room");
                    break;
            }
            lblHetPhong.setText(String.valueOf(countHetPhong));
            lblPhongDat.setText(String.valueOf(countPhongDat));
            lblPhongT.setText(String.valueOf(countPhongTrong));
            lblTongPhong.setText(String.valueOf(countTongPhong));

            btnPhong.setCursor(Cursor.HAND);
            paneDSPhong.getChildren().add(btnPhong); //add button to your pane   
            btnPhong.setOnAction((e) -> {
                try {
                    switch (p.getTrangThai()) {
                        case HETPHONG: {
                            System.err.println(p.getMaPhong());
                            String idPhieuDat = openFormDatPhong(p.getMaPhong());
                            FXMLLoader loader = new FXMLLoader(getClass()
                                    .getResource("views/PhieuThuePhongFXML.fxml"));// set resource file FXML form
                            Parent root = (Parent) loader.load();
                            PhieuThuePhongController control = loader.getController();//form Phieuthuephong
                            control.sendIdPhieuThueVaPhong(idPhieuDat, p.getMaPhong());
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);//stage lock form children
                            stage.setTitle("Quản lý phòng");
                            stage.setScene(scene);
                            stage.show();
                            break;
                        }
                        case DADAT: {

                            FXMLLoader loader = new FXMLLoader(getClass()
                                    .getResource("views/PhieuDatPhongFXML.fxml"));// set resource file FXML form
                            Parent root = (Parent) loader.load();
                            PhieuDatPhongController control = loader.getController();
                            control.sendMaPhieuDat(getMaPhieuDatByIdPhong(p.getMaPhong()));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);//stage lock form children
                            stage.setTitle("Phiếu đặt phòng");
                            stage.setScene(scene);
                            stage.show();
                            break;
                        }
                        default: {
                            FXMLLoader loader = new FXMLLoader(getClass()
                                    .getResource("views/PhieuDatPhongFXML.fxml"));// set resource file FXML form
                            Parent root = (Parent) loader.load();
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);//stage lock form children
                            stage.setTitle("Phiếu đặt phòng");
                            stage.setScene(scene);
                            stage.show();
                            break;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

    }

    private String getMaPhieuDatByIdPhong(String idPhong) {
        String maPhieuDat = "";
        try {
            String sql = "SELECT pd.maphieudat FROM phong as p,"
                    + " chitietdatphong as ct, phieudatphong as pd\n"
                    + "WHERE p.maphong = ct.maphong "
                    + "AND pd.maphieudat = ct.maphieudat\n"
                    + "AND p.maphong = ? AND tinhtrang = ?";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, idPhong);
            p.setString(2, WAITTING);
            ResultSet r = jdbcConfig.ExecuteQuery(p);
            while (r.next()) {
                maPhieuDat = r.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return maPhieuDat;
    }

    public String openFormDatPhong(String idPhong) {
        try {
            String sql = "SELECT pt.maphieuthue FROM chitietdatphong as ct, "
                    + "phieudatphong as pd, phieuthuephong as pt\n"
                    + "WHERE ct.maphieudat = pd.maphieudat AND maphong= ? AND tinhtrang = ?\n"
                    + "AND pt.maphieudat = pd.maphieudat";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, idPhong);
            p.setString(2, BUSY);

            ResultSet r = jdbcConfig.ExecuteQuery(p);
            while (r.next()) {
                return r.getString(1);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jdbcConfig.Connect();
        getDSPhong();
        imgRefresh.setOnMouseClicked((e) -> {
            txtTimKiem.setText("");
            getDSPhong();
        });
        filterHP.setOnMouseClicked((e) -> {
            txtTimKiem.setText(HETPHONG);
            timKiemPhong();
        });
        filterPD.setOnMouseClicked((e) -> {
            txtTimKiem.setText(DADAT);
            timKiemPhong();
        });
        filterPT.setOnMouseClicked((e) -> {
            txtTimKiem.setText(PHONGTRONG);
            timKiemPhong();
        });
    }

}
