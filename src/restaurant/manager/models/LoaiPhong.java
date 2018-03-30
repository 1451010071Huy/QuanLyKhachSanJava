/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.models;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Luxury
 */
@Entity
@Table(name = "loaiphong")
public class LoaiPhong implements Serializable {

    @Id
    @Column(name = "maloai", length = 50, nullable = true)
    private String maLoai;
    @Column(name = "gia", length = Integer.MAX_VALUE, nullable = false)
    private double gia;
    @Column(name = "songuoi", length = Integer.SIZE, nullable = false)
    private int soNguoi;

    public LoaiPhong() {
    }

    public LoaiPhong(String maLoai, double gia, int soNguoi) {
        this.maLoai = maLoai;
        this.gia = gia;
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
