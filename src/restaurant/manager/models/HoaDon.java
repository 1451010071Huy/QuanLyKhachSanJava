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
public class HoaDon {

    private String maPhieuThue;
    private String tenKH;
    private String tenNV;

    public HoaDon() {
    }

    public HoaDon(String maPT, String tenKH, String tenNV) {
        this.maPhieuThue = maPT;
        this.tenKH = tenKH;
        this.tenNV = tenNV;

    }

    /**
     * @return the maPhieuThue
     */
    public String getMaPhieuThue() {
        return maPhieuThue;
    }

    /**
     * @param maPhieuThue the maPhieuThue to set
     */
    public void setMaPhieuThue(String maPhieuThue) {
        this.maPhieuThue = maPhieuThue;
    }

    /**
     * @return the tenKH
     */
    public String getTenKH() {
        return tenKH;
    }

    /**
     * @param tenKH the tenKH to set
     */
    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    /**
     * @return the tenNV
     */
    public String getTenNV() {
        return tenNV;
    }

    /**
     * @param tenNV the tenNV to set
     */
    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }
    
    
}
