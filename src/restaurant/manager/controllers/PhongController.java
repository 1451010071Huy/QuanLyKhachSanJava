/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import restaurant.manager.models.LoaiPhong;

/**
 * FXML Controller class
 *
 * @author Luxury
 */
public class PhongController implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @FXML
    private Button btnCapNhatLP;

    @FXML
    private TextField txtSoNguoi;

    @FXML
    private TextField txtMaLoai;

    @FXML
    private TextField txtGia;

    @FXML
    private ComboBox<?> cbbLoaiPhong;

    @FXML
    private Button btnThemLP;

    @FXML
    private Button btnThemPhong;

    @FXML
    private Button btnXoaLP;

    @FXML
    private Button btnXoaPhong;

    @FXML
    private TableView<LoaiPhong> tblLoaiPhong;

    @FXML
    private TableColumn<LoaiPhong, String> tblColMaLoai;

    @FXML
    private TableColumn<LoaiPhong, Integer> tblColSoNguoi;

    @FXML
    private TableColumn<LoaiPhong, Double> tblColGia;

    @FXML
    private TableView<?> tblPhong;

    @FXML
    private Button btnCapNhatphong;

    @FXML
    private TextField txtPhong;

    private ObservableList<LoaiPhong> oserObservableList;

    private ObservableList<LoaiPhong> getLoaiPhong() {
        Configuration cf = new Configuration();
        cf.configure("hibernate.cfg.xml");
        cf.addAnnotatedClass(LoaiPhong.class);
        StandardServiceRegistryBuilder buider = new StandardServiceRegistryBuilder().applySettings(cf.getProperties());
        SessionFactory sessionFactory = cf.buildSessionFactory(buider.build());
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(LoaiPhong.class);
        List<LoaiPhong> loaiPhong = criteria.list();
        session.close();
        
        oserObservableList = FXCollections.observableArrayList();
        loaiPhong.forEach(lp -> {
            oserObservableList.add(new LoaiPhong(lp.getMaLoai(), lp.getGia(), lp.getSoNguoi()));           
        });
        return oserObservableList;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tblColMaLoai.setCellValueFactory(new PropertyValueFactory<>("maLoai"));
        tblColGia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        tblColSoNguoi.setCellValueFactory(new PropertyValueFactory<>("soNguoi"));
        tblLoaiPhong.setItems(getLoaiPhong());
    }

}
