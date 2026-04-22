/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.models;

/**
 *
 * @author ettore
 */
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
//dependency hell per la validazione, ok java

@Entity
public class rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rentalId")
    private Long rentalID;
    
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private user owner;
    
    private String listingName, description;
    private float pricePerNight;
    @ManyToOne
    @JoinColumn(name = "cityID", nullable = false)
    private cities city;
    
    private String streetName;
    private int zipCode;
    private int floor;

    @ElementCollection
    private List<String> images = new ArrayList<>();
    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }
    
    public rental () {
        
    }

    public Long getRentalID() {
        return rentalID;
    }

    public void setRentalID(Long rentalID) {
        this.rentalID = rentalID;
    }

    public user getOwner() {
        return owner;
    }

    public void setOwner(user owner) {
        this.owner = owner;
    }

    public String getListingName() {
        return listingName;
    }

    public void setListingName(String listingName) {
        this.listingName = listingName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(float pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public cities getCity() {
        return city;
    }

    public void setCity(cities city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
    
    
}
