/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author ettore
 */
@Entity
public class image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long fileID;
    
    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    private rental _rental;
            
    private String path;

    public rental getRental() {
        return _rental;
    }

    public void setRental(rental _rental) {
        this._rental = _rental;
    }

    

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getFileID() {
        return fileID;
    }

    public void setFileID(long fileID) {
        this.fileID = fileID;
    }
    
    
}
