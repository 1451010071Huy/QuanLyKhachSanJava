/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.models;

import java.sql.Date;

/**
 *
 * @author Luxury
 */
public class PhieuDatPhong {
    private String maPhieuDat;
    private String maKhachHang;
    private String ngayDen;
    private String ngayDi;
    private double soTienCoc;
    private String username;
    private String tinhTrang;
    private int soNguoi;

    public PhieuDatPhong(){}
    
    public PhieuDatPhong(String maPD, String maKH, String ngayDen, 
            String ngayDi, String tinhTrang, int soNguoi){
        this.maPhieuDat = maPD;
        this.maKhachHang = maKH;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
        this.tinhTrang = tinhTrang;
        this.soNguoi = soNguoi;
    }
    /**
     * @return the maPhieuDat
     */
    public String getMaPhieuDat() {
        return maPhieuDat;
    }

    /**
     * @param maPhieuDat the maPhieuDat to set
     */
    public void setMaPhieuDat(String maPhieuDat) {
        this.maPhieuDat = maPhieuDat;
    }

    /**
     * @return the maKhachHang
     */
    public String getMaKhachHang() {
        return maKhachHang;
    }

    /**
     * @param maKhachHang the maKhachHang to set
     */
    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    /**
     * @return the ngayDen
     */
    public String getNgayDen() {
        return ngayDen;
    }

    /**
     * @param ngayDen the ngayDen to set
     */
    public void setNgayDen(String ngayDen) {
        this.ngayDen = ngayDen;
    }

    /**
     * @return the ngayDi
     */
    public String getNgayDi() {
        return ngayDi;
    }

    /**
     * @param ngayDi the ngayDi to set
     */
    public void setNgayDi(String ngayDi) {
        this.ngayDi = ngayDi;
    }

    /**
     * @return the soTienCoc
     */
    public double getSoTienCoc() {
        return soTienCoc;
    }

    /**
     * @param soTienCoc the soTienCoc to set
     */
    public void setSoTienCoc(double soTienCoc) {
        this.soTienCoc = soTienCoc;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the tinhTrang
     */
    public String getTinhTrang() {
        return tinhTrang;
    }

    /**
     * @param tinhTrang the tinhTrang to set
     */
    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
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
