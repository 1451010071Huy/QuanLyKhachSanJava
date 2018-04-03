/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.models;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Luxury
 */
public class Phong implements Serializable {

    private String maPhong;
    private String maLoai;
    private double gia;
    private int soNguoi;

    public Phong() {
    }

    public Phong(String maPhong, String maLoai, double gia, int soNguoi) {
        this.maPhong = maPhong;
        this.maLoai = maLoai;
        this.gia = gia;
        this.soNguoi = soNguoi;
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

}
