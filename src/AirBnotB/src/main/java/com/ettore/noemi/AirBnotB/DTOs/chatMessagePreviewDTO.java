package com.ettore.noemi.AirBnotB.DTOs;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class chatMessagePreviewDTO {

    private Long bookingId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status;

    private Long rentalId;
    private String listingName;

    private Long messageId;
    private String message;
    private Long senderId;

    public chatMessagePreviewDTO(Long bookingId, LocalDate checkIn, LocalDate checkOut, String status,
                                 Long rentalId, String listingName,
                                 Long messageId, String message, Long senderId) {
        this.bookingId = bookingId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
        this.rentalId = rentalId;
        this.listingName = listingName;
        this.messageId = messageId;
        this.message = message;
        this.senderId = senderId;
    }

    // getters/setters

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRentalId() {
        return rentalId;
    }

    public void setRentalId(Long rentalId) {
        this.rentalId = rentalId;
    }

    public String getListingName() {
        return listingName;
    }

    public void setListingName(String listingName) {
        this.listingName = listingName;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
    
}
