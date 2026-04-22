/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;

/**
 *
 * @author ettore
 */
import jakarta.persistence.*;
//dependency hell per la validazione, ok java
import java.util.ArrayList;
//Per mappare i listing e le prenotazioni agli utenti (uno a molti (se hai i soldi))
import java.util.List;

@Entity
public class cities {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cityID")
    private Long cityID;
    
    private String cityName;
    private String province;
    private float overnightStayTax;
    
    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private List<rental> rentals = new ArrayList<>();

    public Long getCityID() {
        return cityID;
    }

    public void setCityID(Long cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
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
