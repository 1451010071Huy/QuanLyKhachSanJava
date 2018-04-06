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
public class KhachHang {
    private String maKhachHang;
    private String  tenKhachHang;
    private boolean gioiTinh;
    private String cmnd;
    private String diaChi;
    private String coQuan;
    private String soDienThoai;
    private String email;
    
    public KhachHang(){}
    public KhachHang(String maKH, String tenKH, boolean gioiTinh, String cmnd,
            String diaChi, String coQuan, String sdt, String email){
        this.maKhachHang = maKH;
        this.tenKhachHang = tenKH;
        this.gioiTinh = gioiTinh;
        this.cmnd = cmnd;
        this.diaChi = diaChi;
        this.coQuan = coQuan;
        this.soDienThoai = sdt;
        this.email = email;
    }
    public KhachHang(String tenKH){
        this.tenKhachHang = tenKH;
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
    public boolean getGioiTinh() {
        return gioiTinh;
    }

    /**
     * @param gioiTinh the gioiTinh to set
     */
    public void setGioiTinh(boolean gioiTinh) {
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
     * @return the soDienThoai
     */
    public String getSoDienThoai() {
        return soDienThoai;
    }

    /**
     * @param soDienThoai the soDienThoai to set
     */
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
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
