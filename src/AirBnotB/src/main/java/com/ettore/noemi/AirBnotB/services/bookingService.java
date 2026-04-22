/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.services;

import com.ettore.noemi.AirBnotB.DTOs.bookingDTO;
import com.ettore.noemi.AirBnotB.DTOs.dateRangeDTO;
import com.ettore.noemi.AirBnotB.models.booking;
import com.ettore.noemi.AirBnotB.models.chatMessage;
import com.ettore.noemi.AirBnotB.repositories.bookingRepository;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ettore
 */
@Service
public class bookingService {

    @Autowired
    private bookingRepository bookingRepo;

    public List<dateRangeDTO> getBookedRanges(Long rentalId) {
        return bookingRepo.findConfirmedByRentalId(rentalId)
            .stream()
            .map(b -> {
                dateRangeDTO dto = new dateRangeDTO();
                dto.setFrom(b.getCheckIn());
                dto.setTo(b.getCheckOut());
                return dto;
            })
            .toList();
    }
    public boolean isRentalAvabile (dateRangeDTO _dates, long rentalID) {
        //la repo ritorna false se non c'è una prenotazione, true se c'è quindi not dei valori e si vola.
        return !bookingRepo.existsOverlappingBooking(rentalID, _dates.getFrom(), _dates.getTo()); //no, non ho fumato nulla...
    }
    
    public boolean bookRental (bookingDTO _bookingDTO) {
        booking _booking = new booking();
        _booking.setCheckIn(_bookingDTO.getCheckIn());
        _booking.setCheckOut(_bookingDTO.getCheckOut());
        _booking.setGuest(_bookingDTO.getGuest());
        _booking.setRental(_bookingDTO.getRental());
        _booking.setStatus(_bookingDTO.getStatus());
        bookingRepo.save(_booking);
        return true;
    }
    
    public booking getBookingFromID(long id) {
        return bookingRepo.findById(id).orElseThrow( () -> new RuntimeException("ERRORE: IMPOSSIBILE TROVARE CASA NOI NO PAGHIAMO AFITO"));
    }

}
