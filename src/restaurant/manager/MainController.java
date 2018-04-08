/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager;

import config.jdbcConfig;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private HBox btxQuanLyTaiKhoan;
    @FXML
    private HBox hbxDatPhong;

    public void setUsername(String username) {
        this.lblUser.setText(username);
    }

    private void getDSPhong() {
        try {
            final int kichThuoc = 80;
            jdbcConfig.Connect();
            String sql = "SELECT * FROM phong";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
            ResultSet r = jdbcConfig.ExecuteQuery(p);
            double i = 1;
            int x = 20;
            int y = 20;
            while (r.next()) {
                btnPhong = new Button();
                btnPhong.setMinWidth(kichThuoc);
                btnPhong.setMinHeight(kichThuoc);
                btnPhong.setText(r.getString(1) + "\n" + r.getString(2));
                btnPhong.setLayoutX(x);
                btnPhong.setLayoutY(y);
                x += 100;
                i++;
                if (i == 9) {
                    y += kichThuoc + 20;
                    x = 20;
                    i = 1;
                }
                btnPhong.getStyleClass().removeAll("addBobOk, focus");
                //In this way you're sure you have no styles applied to your object button
                btnPhong.getStyleClass().add("addBobOk");
                btnPhong.setCursor(Cursor.HAND);
                //then you specify the class you would give to the button

                paneDSPhong.getChildren().add(btnPhong); //add button to your pane
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public  void openForm(String resource, String titleStage) {
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
    private void hboxThuePhongClick(MouseEvent event) {
        System.out.println("restaurant.manager.MainController.hboxThuePhongClick()");
    }

    @FXML
    private void hboxDatPhongClick(MouseEvent event) {
        String resource = "views/PhieuDatPhongFXML.fxml";
        String titleScene = "Quản lý đặt phòng";
        openForm(resource, titleScene);

    }

    @FXML
    private void hboxHoaDonClick(MouseEvent event) {
        System.out.println("restaurant.manager.MainController.hboxHoaDonClick()");
    }

    @FXML
    private void hboxNhanVienClick(MouseEvent event) {
        String resource = "views/NhanVienFXML.fxml";
        String titleScene = "Quản lý nhân viên";
        openForm(resource, titleScene);
    }

    @FXML
    private void hboxKhachHangClick(MouseEvent event) {
        String resource = "views/KhachHangFXML.fxml";
        String titleScene = "Quản lý khách hàng";
        openForm(resource, titleScene);
    }

    @FXML
    private void hboxDichVuClick(MouseEvent event) {
        String resource = "views/DichVuFXML.fxml";
        String titleScene = "Quản lý dịch vụ";
        openForm(resource, titleScene);
    }

    @FXML
    private void hboxPhongClick(MouseEvent event) {
        String resource = "views/PhongFXML.fxml";
        String titleScene = "Quản lý phòng";
        openForm(resource, titleScene);
    }

    @FXML
    private void hboxBaoCaoClick(MouseEvent event) {

    }

    @FXML
    private void hboxQLTaiKhoanClick(MouseEvent event) {
        System.out.println("restaurant.manager.MainController.hboxQLTaiKhoan()");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getDSPhong();
    }

}
