package com.ettore.noemi.AirBnotB.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;

@Entity
public class rentalRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingID;

    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    @JsonIgnore
    private rental evaluatedRental;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private user ratingUser;

    private String evaluation;

    @Positive
    private int stars;

    // Getters & Setters
    public Long getRatingID() { return ratingID; }
    public rental getEvaluatedRental() { return evaluatedRental; }
    public void setEvaluatedRental(rental evaluatedRental) { this.evaluatedRental = evaluatedRental; }
    public user getRatingUser() { return ratingUser; }
    public void setRatingUser(user ratingUser) { this.ratingUser = ratingUser; }
    public String getEvaluation() { return evaluation; }
    public void setEvaluation(String evaluation) { this.evaluation = evaluation; }
    public int getStars() { return stars; }
    public void setStars(int stars) { this.stars = stars; }
}
