/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import config.jdbcConfig;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import restaurant.manager.MainController;

/**
 * FXML Controller class
 *
 * @author Luxury
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField txtDangNhap;
    @FXML
    private JFXPasswordField txtMatKhau;
    @FXML
    private JFXButton btnDangNhap;
    @FXML
    private Label lblStatus;

    public String checkDangNhap() {
        if ("".equals(txtDangNhap.getText())) {
            return "Mật khẩu không được để trống";
        } else if ("".equals(txtMatKhau.getText())) {
            return "Tên đăng nhập không được để trống";
        } else {
            try {
                String sql = "SELECT username, password FROM hethong ";
                PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
                ResultSet r = jdbcConfig.ExecuteQuery(p);
                while (r.next()) {
                    if (r.getString(1).equals(txtDangNhap.getText())
                            && r.getString(2).equals(txtMatKhau.getText())) {
                        FXMLLoader loader = new FXMLLoader(getClass()
                                .getResource("/restaurant/manager/MainFXML.fxml"));
                        Parent root;
                        try {
                            root = (Parent) loader.load();
                            MainController control = loader.getController();
                            control.setUsername(r.getString(1));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);//stage lock form children
                            stage.setTitle("Trang chủ");
                            stage.setScene(scene); 
                            stage.show();
                            
                            //close form login
                            stage = (Stage) btnDangNhap.getScene().getWindow();
                            stage.close();
                        } catch (IOException ex) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        return "Đăng nhập thành công";
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "Tài khoản hoặc mật khẩu không đúng";
    }

    public void buttonPressed(KeyEvent e) {
        if (e.getCode().toString().equals("ENTER")) {

        }
    }

    @FXML
    public void textAction(KeyEvent e) {
        if (e.getCode().equals(KeyCode.ENTER)) {
            lblStatus.setText(checkDangNhap());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        jdbcConfig.Connect();
        btnDangNhap.setOnAction((e) -> {
            lblStatus.setText(checkDangNhap());
        });
    }

}
