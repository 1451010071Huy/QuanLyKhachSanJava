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
public class HeThong {
   
    private String manhanvien;
    private String username;
    private String password;

    public HeThong(){}
    public HeThong(String manhanvien ,String user, String pass){
        this.manhanvien = manhanvien;
        this.username = user;
        this.password = pass;
    }
    
    /**
     * @return the manhanvien
     */
    public String getManhanvien() {
        return manhanvien;
    }

    /**
     * @param manhanvien the manhanvien to set
     */
    public void setManhanvien(String manhanvien) {
        this.manhanvien = manhanvien;
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

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
