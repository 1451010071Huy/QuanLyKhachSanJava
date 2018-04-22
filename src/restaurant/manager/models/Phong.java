/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.models;

import java.io.Serializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

/**
 *
 * @author Luxury
 */
public class Phong implements Serializable {

    /**
     * @return the trangThai
     */
    public String getTrangThai() {
        return trangThai;
    }

    /**
     * @param trangThai the trangThai to set
     */
    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    /**
     * @return the xoaPhong
     */
    public Button getXoaPhong() {
        return xoaPhong;
    }

    /**
     * @param xoaPhong the xoaPhong to set
     */
    public void setXoaPhong(Button xoaPhong) {
        this.xoaPhong = xoaPhong;
    }

    private String maPhong;
    private String maLoai;
    private String trangThai;
    private double gia;
    private int soNguoi;
    private CheckBox chonPhong;
    private Button xoaPhong;
    
    public Phong() {
    }

    public Phong(String maPhong, String maLoai, double gia, int soNguoi, String trangThai) {
        this.maPhong = maPhong;
        this.maLoai = maLoai;
        this.gia = gia;
        this.soNguoi = soNguoi;
        this.trangThai = trangThai;
        this.chonPhong = new CheckBox();
        this.xoaPhong = new Button("X");
    }

    /**
     * @return the maPhong
     */
    public String getMaPhong() {
        return maPhong;
    }

    /**
     * @param maPhong the maPhong to set
     */
    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    /**
     * @return the maLoai
     */
    public String getMaLoai() {
        return maLoai;
    }

    /**
     * @param maLoai the maLoai to set
     */
    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    /**
     * @return the gia
     */
    public double getGia() {
        return gia;
    }

    /**
     * @param gia the gia to set
     */
    public void setGia(double gia) {
        this.gia = gia;
    }

    /**
     * @return the soNguoi
     */
    public int getSoNguoi() {
        return soNguoi;
    }

    /**
     * @param soNguoi the soNguoi to set
     */
    public void setSoNguoi(int soNguoi) {
        this.soNguoi = soNguoi;
    }

    /**
     * @return the chonPhong
     */
    public CheckBox getChonPhong() {
        return chonPhong;
    }

    /**
     * @param chonPhong the chonPhong to set
     */
    public void setChonPhong(CheckBox chonPhong) {
        this.chonPhong = chonPhong;
    }

    /**
     * @return the selectPhong
     */
}
