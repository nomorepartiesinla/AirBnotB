/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.DTOs;

/**
 *
 * @author ettore
 */
import com.ettore.noemi.AirBnotB.models.cities;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
public class rentalDTO {
    @NotNull(message = "Errore fatale: mismatch ID dell'annuncio!")
    private Long rentalID;

    @NotNull(message = "Errore fatale: impossibile trovare proprietario dell'annuncio")
    private Long ownerID;

    @NotBlank(message = "Il nome dell'annuncio è vuoto")
    private String listingName;

    @NotBlank(message = "La descrizione è vuota")
    private String description;

    @Positive(message = "Il prezzo deve essere maggiore di 0")
    private float pricePerNight;

    @NotNull(message = "Errore: non è stata selezionata una città!")
    private cities city;

    @NotBlank(message = "errore: indirizzo incompleto!")
    private String streetName;

    @Positive(message = "ZipCode non valido")
    private int zipCode;

    private int floor;
    
    //gettteeeeerrr e tung tung tung settur

    public Long getRentalID() {
        return rentalID;
    }

    public void setRentalID(Long rentalID) {
        this.rentalID = rentalID;
    }

    public Long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Long ownerID) {
        this.ownerID = ownerID;
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
