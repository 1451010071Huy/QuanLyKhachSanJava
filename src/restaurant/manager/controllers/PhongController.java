package restaurant.manager.controllers;





/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import config.jdbcConfig;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import restaurant.manager.models.LoaiPhong;
import restaurant.manager.models.Phong;
import restaurant.manager.models.TrangThaiPhong;

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
    private TableColumn<Phong, Double> tblColGia1;

    @FXML
    private TextField txtSoNguoi;

    @FXML
    private TableColumn<LoaiPhong, Double> tblColGia;

    @FXML
    private TextField txtMaLoai;

    @FXML
    private TextField txtGia;

    @FXML
    private ComboBox<String> cbbLoaiPhong;
    
    @FXML
    private ComboBox<TrangThaiPhong> cbbTrangThaiPhong;
    
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
    private TableView<Phong> tblPhong;

    @FXML
    private TableColumn<Phong, String> tblColTrangThai;
    
    @FXML
    private TableColumn<Phong, Integer> tblColSoNguoi1;

    @FXML
    private TableColumn<Phong, String> tblColMaPhong;
    @FXML

    private TableColumn<Phong, String> tblColLoaiPhong1;
    @FXML
    private Button btnCapNhatphong;
    
    @FXML
    private TextField txtPhong;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private ObservableList<LoaiPhong> listLoaiPhong;
    private ObservableList<Phong> listPhong;

    private ObservableList<LoaiPhong> getLoaiPhong() throws SQLException {
        String sql = "SELECT * FROM loaiphong";
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);

        ResultSet r = jdbcConfig.ExecuteQuery(p);//Thực thi câu truy vấn
        listLoaiPhong = FXCollections.observableArrayList();
        while (r.next()) {
            listLoaiPhong.add(new LoaiPhong(r.getString(1),
                    Double.parseDouble(r.getString(2)),
                    Integer.parseInt(r.getString(3))));
        }
        return listLoaiPhong;
    }

    private void insertLoaiPhong() throws SQLException {
        String sql = String.format("INSERT INTO loaiphong(maloai, gia, songuoi)"
                + "VALUES (?, ?, ?)");

        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtMaLoai.getText());
        p.setString(2, txtGia.getText());
        p.setString(3, txtSoNguoi.getText());
        int rows = jdbcConfig.ExecuteUpdateQuery(p);
        if (rows != 0) {
            setTableLoaiPhong(getLoaiPhong());
            thongBao();
            alert.setContentText("Thêm Thành Công");
        }
    }

    private void insertPhong() throws SQLException {
        String id = util.RandomId.createNewID("PH");
        String sql = String.format("INSERT INTO phong(maphong ,maloai,trangthai) "
                + "VALUES (?, ?, ?)");
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtPhong.getText());
        p.setString(2, cbbLoaiPhong.getValue());
        p.setString(3, cbbTrangThaiPhong.getValue().toString());
        int rows = jdbcConfig.ExecuteUpdateQuery(p);
        if (rows != 0) {
            setTablePhong(getPhong());
            thongBao();
            alert.setContentText("Thêm Thành Công");
        }
    }
    private ObservableList<TrangThaiPhong> getTrangThaiPhong() {
        TrangThaiPhong ttp1 = new TrangThaiPhong("Phòng Trống");
        ObservableList<TrangThaiPhong> list = FXCollections.observableArrayList(ttp1);
        return list;
    }
    private void getLPhong() throws SQLException {
        String sql = "SELECT maloai FROM loaiphong";
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        ResultSet r = jdbcConfig.ExecuteQuery(p);
        while (r.next()) {
            cbbLoaiPhong.getItems().add(r.getString(1));
        }
    }
    
    private ObservableList<Phong> getPhong() throws SQLException {
        try {
            String sql = "SELECT p.maphong, lp.maloai, lp.gia, lp.songuoi, p.trangthai \n"
                    + "FROM loaiphong as lp, phong as p\n"
                    + "WHERE  p.maloai = lp.maloai ";
            PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);

            ResultSet r = jdbcConfig.ExecuteQuery(p);//Thực thi câu truy vấn
            listPhong = FXCollections.observableArrayList();
            while (r.next()) {
                listPhong.add(new Phong(r.getString(1),
                r.getString(2),Double.parseDouble(r.getString(3)),
                Integer.parseInt(r.getString(4)),r.getString(5)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listPhong;
    }

    private void setTableLoaiPhong(ObservableList e) throws SQLException {
        tblLoaiPhong.setItems(e);//Lấy giá trị DB rồi set cho bảng 
        tblColMaLoai.setCellValueFactory(new PropertyValueFactory<>("maLoai"));//set mã loại (với mã loại là thuộc tính của model)
        tblColGia.setCellValueFactory(new PropertyValueFactory<>("gia"));
        tblColSoNguoi.setCellValueFactory(new PropertyValueFactory<>("soNguoi"));
    }

    private void setTablePhong(ObservableList e) throws SQLException {
        tblPhong.setItems(e);//Lấy giá trị DB rồi set cho bảng   
        tblColMaPhong.setCellValueFactory(new PropertyValueFactory<>("maPhong"));//set mã loại (với mã loại là thuộc tính của model)
        tblColLoaiPhong1.setCellValueFactory(new PropertyValueFactory<>("maLoai"));
        tblColGia1.setCellValueFactory(new PropertyValueFactory<>("gia"));
        tblColSoNguoi1.setCellValueFactory(new PropertyValueFactory<>("soNguoi"));
        tblColTrangThai.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
    }

    @FXML
    private void SelectRowPhong(MouseEvent e) throws SQLException {
        if (e.getClickCount() == 1) {
            txtPhong.setText(tblPhong.getSelectionModel()
                    .getSelectedItem().getMaPhong());
            cbbLoaiPhong.setValue((tblPhong.getSelectionModel()
                    .getSelectedItem().getMaLoai()));
            cbbTrangThaiPhong.setValue(new TrangThaiPhong(tblPhong.getSelectionModel()
                    .getSelectedItem().getTrangThai()));
        }
    }

    @FXML
    private void SelectRowLoaiPhong(MouseEvent e) throws SQLException {
        if (e.getClickCount() == 1) {
            txtMaLoai.setText(tblLoaiPhong.getSelectionModel()
                    .getSelectedItem().getMaLoai());
            txtGia.setText(String.valueOf(tblLoaiPhong.getSelectionModel()
                    .getSelectedItem().getGia()));
            txtSoNguoi.setText(Integer.toString(tblLoaiPhong.getSelectionModel()
                    .getSelectedItem().getSoNguoi()));
        }
    }

    private void deleteLoaiPhong() throws SQLException {
        String sql = String.format("DELETE FROM loaiphong\n"
                + "WHERE maloai = ?");
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtMaLoai.getText());
        int row = p.executeUpdate();
        if (row == 1) {
            setTableLoaiPhong(getLoaiPhong());
            thongBao();
            alert.setContentText("Xóa Thành Công");
        }
    }

    private void deletePhong() throws SQLException {
        String sql = String.format("DELETE FROM phong\n"
                + "WHERE maphong = ?");
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtPhong.getText());
        int row = p.executeUpdate();
        if (row == 1) {
            setTablePhong(getPhong());
            thongBao();
            alert.setContentText("Xóa Thành Công");
        }
    }

    private void updateLoaiPhong() throws SQLException {
        String sql = String.format("UPDATE loaiphong \n"
                + "SET gia = ?,\n"
                + "songuoi = ?\n"
                + "WHERE maloai = ? ");
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);
        p.setString(1, txtGia.getText());
        p.setString(2, txtSoNguoi.getText());
        p.setString(3, txtMaLoai.getText());
        int row = p.executeUpdate();
        if (row == 1) {
            setTableLoaiPhong(getLoaiPhong());
            thongBao();
            alert.setContentText("Sửa Thành Công");
        }
    }

    private void updatePhong() throws SQLException {
        String sql = String.format("UPDATE phong SET \n"
                + "maloai = ?\n"
                + "WHERE maphong = ? ");
        PreparedStatement p = jdbcConfig.connection.prepareStatement(sql);

        p.setString(1, cbbLoaiPhong.getValue());
        p.setString(2, txtPhong.getText());
        int row = p.executeUpdate();
        if (row == 1) {
            setTablePhong(getPhong());
            thongBao();
            alert.setContentText("Sửa Thành Công");
        }
    }
    private void thongBao(){
        alert.setTitle("Thông Báo");
        alert.setHeaderText(null);
        alert.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        jdbcConfig.Connect();
        try {   
            getLPhong();
            ObservableList<TrangThaiPhong> listttp = getTrangThaiPhong();
            cbbTrangThaiPhong.getItems().addAll(listttp);
            cbbTrangThaiPhong.getSelectionModel().select(0);
            setTableLoaiPhong(getLoaiPhong());
            setTablePhong(getPhong());
            btnThemLP.setOnAction(e -> {
                try {
                    insertLoaiPhong();
                } catch (SQLException ex) {
                    Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            btnXoaLP.setOnAction(e -> {
                try {
                    deleteLoaiPhong();
                } catch (SQLException ex) {
                    Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            btnCapNhatLP.setOnAction(e -> {
                try {
                    updateLoaiPhong();
                } catch (SQLException ex) {
                    Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            btnThemPhong.setOnAction(e -> {
                try {
                    insertPhong();
                } catch (SQLException ex) {
                    Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            btnXoaPhong.setOnAction(e -> {
                try {
                    deletePhong();
                } catch (SQLException ex) {
                    Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            btnCapNhatphong.setOnAction(e -> {
                try {
                    updatePhong();
                } catch (SQLException ex) {
                    Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            
        } catch (SQLException ex) {
            Logger.getLogger(PhongController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
