/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.models;

import java.io.Serializable;

/**
 *
 * @author sky
 */
public class KhachHang implements Serializable{

    private String maKhachHang;
    private String tenKhachHang;
    private String gioiTinh;
    private String cmnd;
    private String diaChi;
    private String coQuan;
    private String sdt;
    private String email;
    
    public KhachHang(){
        
    }
    
    public KhachHang(String maKhachHang , String tenKhachHang , String gioiTinh, 
         String cmnd , String diaChi, String coQuan ,String sdt , String email){
        
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.gioiTinh = gioiTinh;
        this.cmnd = cmnd;
        this.diaChi = diaChi;
        this.coQuan = coQuan;
        this.sdt = sdt;
        this.email = email;
    }
    /**
     * @return the maKhangHang
     */
    public String getMaKhachHang() {
        return maKhachHang;
    }

    /**
     * @param maKhangHang the maKhangHang to set
     */
    public void setMaKhachHang(String maKhangHang) {
        this.maKhachHang = maKhangHang;
    }

    /**
     * @return the tenKhachHang
     */
    public String getTenKhachHang() {
        return tenKhachHang;
    }

    /**
     * @param tenKhachHang the tenKhachHang to set
     */
    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }
    
       /**
     * @return the gioiTinh
     */
    public String getGioiTinh() {
        return gioiTinh;
    }

    /**
     * @param gioiTinh the gioiTinh to set
     */
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    

    /**
     * @return the cmnd
     */
    public String getCmnd() {
        return cmnd;
    }

    /**
     * @param cmnd the cmnd to set
     */
    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    /**
     * @return the diaChi
     */
    public String getDiaChi() {
        return diaChi;
    }

    /**
     * @param diaChi the diaChi to set
     */
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    /**
     * @return the coQuan
     */
    public String getCoQuan() {
        return coQuan;
    }

    /**
     * @param coQuan the coQuan to set
     */
    public void setCoQuan(String coQuan) {
        this.coQuan = coQuan;
    }

    /**
     * @return the sdt
     */
    public String getSdt() {
        return sdt;
    }

    /**
     * @param sdt the sdt to set
     */
    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
  
}
