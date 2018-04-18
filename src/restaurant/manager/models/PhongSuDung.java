/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.models;

/**
 *
 * @author Luxury
 */
public class PhongSuDung {

    private String maPhong;
    private double giaPhong;
    private String ngayDen;
    private int soNgaySuDung;
    private double thanhTien;

    public PhongSuDung() {
    }

    public PhongSuDung(String maPhong, double giaPhong, String ngayDen,
            int soNgaySuDung, double thanhTien) {
        this.maPhong = maPhong;
        this.giaPhong = giaPhong;
        this.ngayDen = ngayDen;
        this.soNgaySuDung = soNgaySuDung;
        this.thanhTien = thanhTien;
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
     * @return the giaPhong
     */
    public double getGiaPhong() {
        return giaPhong;
    }

    /**
     * @param giaPhong the giaPhong to set
     */
    public void setGiaPhong(double giaPhong) {
        this.giaPhong = giaPhong;
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
     * @return the soNgaySuDung
     */
    public int getSoNgaySuDung() {
        return soNgaySuDung;
    }

    /**
     * @param soNgaySuDung the soNgaySuDung to set
     */
    public void setSoNgaySuDung(int soNgaySuDung) {
        this.soNgaySuDung = soNgaySuDung;
    }

    /**
     * @return the thanhTien
     */
    public double getThanhTien() {
        return thanhTien;
    }

    /**
     * @param thanhTien the thanhTien to set
     */
    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
    

}
