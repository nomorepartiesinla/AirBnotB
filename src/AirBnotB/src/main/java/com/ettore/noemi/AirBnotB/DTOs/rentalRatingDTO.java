/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author ettore
 */
public class rentalRatingDTO {
    
    @NotNull(message = "Rental ID mancante")
    private Long rentalID;

    @NotNull(message = "User ID mancante")
    private Long userID;

    @NotBlank(message = "La valutazione testuale non può essere vuota")
    private String evaluation;

    @Min(value = 1, message = "Le stelle devono essere almeno 1")
    @Max(value = 5, message = "Le stelle non possono superare 5")
    private int stars;

    // costruttore vuoto (OBBLIGATORIO per Jackson)
    public rentalRatingDTO() {}

    public Long getRentalID() {
        return rentalID;
    }

    public void setRentalID(Long rentalID) {
        this.rentalID = rentalID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
