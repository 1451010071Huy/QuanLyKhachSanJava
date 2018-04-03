/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.models;

import java.io.Serializable;

public class LoaiPhong implements Serializable {

    private String maLoai;
    private double giaCa;
    private int soNguoi;

    public LoaiPhong() {
    }

    public LoaiPhong(String maLoai, double gia, int soNguoi) {
        this.maLoai = maLoai;
        this.giaCa = gia;
        this.soNguoi = soNguoi;
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
        return giaCa;
    }

    /**
     * @param gia the gia to set
     */
    public void setGia(double gia) {
        this.giaCa = gia;
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
