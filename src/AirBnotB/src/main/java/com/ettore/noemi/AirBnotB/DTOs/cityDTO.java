/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.DTOs;

import com.ettore.noemi.AirBnotB.models.rental;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.*;
/**
 *
 * @author ettore
 */
public class cityDTO {
    @NotEmpty(message = "Errore fatale: città deformata!")
    private Long cityID;
    @NotEmpty(message = "Errore fatale: città senza nome")
    private String name;
    @NotEmpty(message = "Errore fatale: città senza provincia")
    private String province;
    @NotEmpty(message = "Errore fatale: tassazione non aggiornata")
    private float overnightStayTax;
    
    @OneToMany(mappedBy = "city")
    private List<rental> rentals = new ArrayList<>();
   
    
    //tung tung setter e **** ****
    public Long getCityID() {
        return cityID;
    }

    public void setCityID(Long cityID) {
        this.cityID = cityID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public float getOvernightStayTax() {
        return overnightStayTax;
    }

    public void setOvernightStayTax(float overnightStayTax) {
        this.overnightStayTax = overnightStayTax;
    }

    public List<rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<rental> rentals) {
        this.rentals = rentals;
    }
    
}
