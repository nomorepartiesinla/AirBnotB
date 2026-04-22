/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;

/**
 *
 * @author ettore
 */
@Entity
public class chatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageID")
    private Long messageID;
    
    @JoinColumn(name = "reservation")
    @ManyToOne
    @JsonIgnore
    booking booking;
    
    private String message;
    
    //@JsonIgnore
    @ManyToOne
    private user sender;
    
    private LocalDate sentTime;
    
    private boolean hasMessageBeenRead;

    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public booking getBooking() {
        return booking;
    }

    public void setBooking(booking booking) {
        this.booking = booking;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public user getSender() {
        return sender;
    }

    public void setSender(user sender) {
        this.sender = sender;
    }

    public LocalDate getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDate sentTime) {
        this.sentTime = sentTime;
    }

    public boolean isHasMessageBeenRead() {
        return hasMessageBeenRead;
    }

    public void setHasMessageBeenRead(boolean hasMessageBeenRead) {
        this.hasMessageBeenRead = hasMessageBeenRead;
    }  
}
