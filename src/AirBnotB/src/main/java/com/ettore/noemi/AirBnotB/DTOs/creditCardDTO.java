/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.DTOs;
import jakarta.validation.constraints.*;

/**
 *
 * @author ettore
 */
public class creditCardDTO {
    private long number;
    private String cardholderName;
    
    @NotNull
    @Min (value = 1)
    @Max (value= 12)
    private int mm; 
    
    @NotNull
    @Max (value=9999)               //amex come al solito fa come gli gira a loro
    private int cvv;
    
    @NotNull
    @Min (value=2026)
    @Max (value=2036)
    private int yyyy;

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public int getMm() {
        return mm;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public int getYyyy() {
        return yyyy;
    }

    public void setYyyy(int yyyy) {
        this.yyyy = yyyy;
    }
    
    
    
    
}
