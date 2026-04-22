/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.DTOs;

/**
 *
 * @author ettore
 */
public class purchaseStayDTO {
    //wrapper del wrapper del wrapper perchè springboot è un cestino
    private creditCardDTO cc;
    private dateRangeDTO date;
    private rentalDTO rental;

    public creditCardDTO getCc() {
        return cc;
    }

    public void setCc(creditCardDTO cc) {
        this.cc = cc;
    }

    public dateRangeDTO getDate() {
        return date;
    }

    public void setDate(dateRangeDTO date) {
        this.date = date;
    }

    public rentalDTO getRental() {
        return rental;
    }

    public void setRental(rentalDTO rental) {
        this.rental = rental;
    }
    
    
}
