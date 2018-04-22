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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    ObservableList<Phong> listPhong = null;

    public void setUsername(String username) {
        this.lblUser.setText(username);
    }

    private ObservableList<Phong> getDSPhong() {
        try {
            String sql = "SELECT * FROM phong";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            ResultSet r = jdbcConfig.ExecuteQuery(p);
            listPhong = FXCollections.observableArrayList();
            while (r.next()) {
                listPhong.add(new Phong(r.getString(1), r.getString(2)));
            }

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
                System.out.println("admin");
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
                    + "WHERE maphong LIKE ? OR maloai LIKE ?";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            p.setString(1, "%" + txtTimKiem.getText().trim() + "%");
            p.setString(2, "%" + txtTimKiem.getText().trim() + "%");
            ResultSet r = jdbcConfig.ExecuteQuery(p);

            while (r.next()) {
                listPhong.add(new Phong(r.getString(1), r.getString(2)));
            }
            drawPhong();
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
            lblStatus.setText("   Không tìm thấy phòng nào.\n"
                    + "Vui lòng sử dụng từ khóa khác!");
        } else {
            lblStatus.setText("");
        }
        paneDSPhong.getChildren().add(lblStatus);
    }

    public void drawPhong() {
        double i = 1;
        int x = 20;
        int y = 20;
        for (Phong p : listPhong) {
            final int kichThuoc = 80;
            btnPhong = new Button();
            btnPhong.setMinWidth(kichThuoc);
            btnPhong.setMinHeight(kichThuoc);
            btnPhong.setText(p.getMaPhong() + "\n" + p.getMaLoai());
            btnPhong.setLayoutX(x);
            btnPhong.setLayoutY(y);
            x += 100;
            i++;
            if (i == 9) {
                y += kichThuoc + 20;
                x = 20;
                i = 1;
            }
            btnPhong.getStyleClass().add("room");
            btnPhong.setCursor(Cursor.HAND);
            //then you specify the class you would give to the button
            paneDSPhong.getChildren().add(btnPhong); //add button to your pane   
        }
        
    }
    
    private void contextMenu(){
        paneDSPhong.setOnMouseClicked((e->{
            
        }));
        ContextMenu contextMenu = new ContextMenu();
        MenuItem hoaDon = new MenuItem("Hóa đơn");
        contextMenu.getItems().add(hoaDon);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getDSPhong();
        drawPhong();
    }

}
