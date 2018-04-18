/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.models;

import javafx.scene.control.Button;

/**
 *
 * @author Luxury
 */
public class DichVuSuDung {

    private String maDichVu;
    private String tenDichVu;
    private String ngaySuDung;
    private int soLuong;
    private String donViTinh;
    private double gia;
    private double thanhTien;
    private Button btnXoaDV;
    private String maPhong;

    public DichVuSuDung() {
    }

    public DichVuSuDung(String tenDV, String ngaySuDung, int soLuong,
            String donViTinh, double gia, double thanhTien, String maDV) {
        this.tenDichVu = tenDV;
        this.ngaySuDung = ngaySuDung;
        this.soLuong = soLuong;
        this.donViTinh = donViTinh;
        this.gia = gia;
        this.thanhTien = thanhTien;
        btnXoaDV = new Button("Xóa");
        this.maDichVu = maDV;
    }

    public DichVuSuDung(String tenDV, String maPhong, String ngaySuDung, int soLuong,
            String donViTinh, double gia, double thanhTien) {
        this.tenDichVu = tenDV;
        this.maPhong = maPhong;
        this.ngaySuDung = ngaySuDung;
        this.soLuong = soLuong;
        this.gia = gia;
        this.donViTinh = donViTinh;
        this.thanhTien = thanhTien;
    }
   
    /**
     * @return the tenDichVu
     */
    public String getTenDichVu() {
        return tenDichVu;
    }

    /**
     * @param tenDichVu the tenDichVu to set
     */
    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    /**
     * @return the ngaySuDung
     */
    public String getNgaySuDung() {
        return ngaySuDung;
    }

    /**
     * @param ngaySuDung the ngaySuDung to set
     */
    public void setNgaySuDung(String ngaySuDung) {
        this.ngaySuDung = ngaySuDung;
    }

    /**
     * @return the soLuong
     */
    public int getSoLuong() {
        return soLuong;
    }

    /**
     * @param soLuong the soLuong to set
     */
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    /**
     * @return the donViTinh
     */
    public String getDonViTinh() {
        return donViTinh;
    }

    /**
     * @param donViTinh the donViTinh to set
     */
    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
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
     * @return the btnXoaDV
     */
    public Button getBtnXoaDV() {
        return btnXoaDV;
    }

    /**
     * @param btnXoaDV the btnXoaDV to set
     */
    public void setBtnXoaDV(Button btnXoaDV) {
        this.btnXoaDV = btnXoaDV;
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

    /**
     * @return the maDichVu
     */
    public String getMaDichVu() {
        return maDichVu;
    }

    /**
     * @param maDichVu the maDichVu to set
     */
    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
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

}
