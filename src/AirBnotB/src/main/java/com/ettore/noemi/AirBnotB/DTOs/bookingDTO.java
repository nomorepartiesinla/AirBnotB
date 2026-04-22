/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.DTOs;

import com.ettore.noemi.AirBnotB.models.BookingStatus;
import com.ettore.noemi.AirBnotB.models.rental;
import com.ettore.noemi.AirBnotB.models.user;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

/**
 *
 * @author ettore
 */
public class bookingDTO {
    
    private Long id;

    
    private rental rental;

    
    private user guest;

    private LocalDate checkIn;   // inclusive
    private LocalDate checkOut;  // exclusive

    
    private BookingStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public rental getRental() {
        return rental;
    }

    public void setRental(rental rental) {
        this.rental = rental;
    }

    public user getGuest() {
        return guest;
    }

    public void setGuest(user guest) {
        this.guest = guest;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }
    
    
}
