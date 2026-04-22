/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.DTOs;

import com.ettore.noemi.AirBnotB.models.booking;
import com.ettore.noemi.AirBnotB.models.user;
import java.time.LocalDate;

/**
 *
 * @author ettore
 */
public class messageDTO {
    private Long messageID;
    
    booking booking;
    
    private String message;
    
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
