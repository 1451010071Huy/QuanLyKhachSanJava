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
public class TrangThaiPhong {

   
    private String name;
    public TrangThaiPhong(){
        
    }
    public TrangThaiPhong(String name){
        this.name = name;
    }
     /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}
