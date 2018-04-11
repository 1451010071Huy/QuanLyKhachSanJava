/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.models;

import java.sql.Timestamp;

/**
 *
 * @author Luxury
 */
public class KiemTraPhong {

    private String maPhieuDat;
    private String maPhong;
    private Timestamp ngayDen;
    private Timestamp ngayDi;
    private String tinhTrang;

    public KiemTraPhong() {
    }

    public KiemTraPhong(String maPhieuDat, String maPhong,
            Timestamp ngayDen, Timestamp ngayDi, String tinhTrang) {
        this.maPhieuDat = maPhieuDat;
        this.maPhong = maPhong;
        this.ngayDen = ngayDen;
        this.ngayDi = ngayDi;
        this.tinhTrang = tinhTrang;
    }

    public KiemTraPhong(String maPhong) {
        this.maPhong = maPhong;

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
     * @return the ngayDen
     */
    public Timestamp getNgayDen() {
        return ngayDen;
    }

    /**
     * @param ngayDen the ngayDen to set
     */
    public void setNgayDen(Timestamp ngayDen) {
        this.ngayDen = ngayDen;
    }

    /**
     * @return the ngayDi
     */
    public Timestamp getNgayDi() {
        return ngayDi;
    }

    /**
     * @param ngayDi the ngayDi to set
     */
    public void setNgayDi(Timestamp ngayDi) {
        this.ngayDi = ngayDi;
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
}
