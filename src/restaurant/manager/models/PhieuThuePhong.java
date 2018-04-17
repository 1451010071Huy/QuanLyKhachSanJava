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
public class PhieuThuePhong {

    private String maPhieuThue;
    private String maphieuDat;
    private String username;

    public PhieuThuePhong() {
    }

    public PhieuThuePhong(String maPT, String maPD) {
        this.maPhieuThue = maPT;
        this.maphieuDat = maPD;
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
     * @return the maphieuDat
     */
    public String getMaphieuDat() {
        return maphieuDat;
    }

    /**
     * @param maphieuDat the maphieuDat to set
     */
    public void setMaphieuDat(String maphieuDat) {
        this.maphieuDat = maphieuDat;
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
}
