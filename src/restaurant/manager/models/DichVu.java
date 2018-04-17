/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.manager.models;

/**
 *
 * @author sky
 */
public class DichVu {

    private String maDichVu;
    private String tenDichVu;
    private int gia;
    private String donViTinh;

    public DichVu() {

    }

    public DichVu(String maDichVu, String tenDichVu, int gia, String donViTinh) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
        this.gia = gia;
        this.donViTinh = donViTinh;
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
     * @return the gia
     */
    public int getGia() {
        return gia;
    }

    /**
     * @param gia the gia to set
     */
    public void setGia(int gia) {
        this.gia = gia;
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

}
