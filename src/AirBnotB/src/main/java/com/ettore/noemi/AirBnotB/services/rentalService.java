/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.services;

import com.ettore.noemi.AirBnotB.DTOs.advancedSearchDTO;
import com.ettore.noemi.AirBnotB.DTOs.bookingDTO;
import com.ettore.noemi.AirBnotB.DTOs.cityDTO;
import com.ettore.noemi.AirBnotB.DTOs.creditCardDTO;
import com.ettore.noemi.AirBnotB.DTOs.dateRangeDTO;
import com.ettore.noemi.AirBnotB.DTOs.rentalDTO;
import com.ettore.noemi.AirBnotB.DTOs.userDTO;
import com.ettore.noemi.AirBnotB.models.rental;
import com.ettore.noemi.AirBnotB.repositories.rentalRepository;
import com.ettore.noemi.AirBnotB.authUtils.JwtUtil;
import com.ettore.noemi.AirBnotB.finance.CreditCardSafety;
import com.ettore.noemi.AirBnotB.models.BookingStatus;
import com.ettore.noemi.AirBnotB.models.user;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ettore
 */
@Service

public class rentalService {

    @Autowired
    private rentalRepository rentalRepo;

    @Autowired
    private cityService city;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private userService _user;
    
    @Autowired
    private bookingService _bookingService;
    
    public void createRental (rentalDTO newRentalDTO, String authToken) {
        rental newRental = new rental();
        
        //nome listing
        newRental.setListingName(newRentalDTO.getListingName());
        newRental.setDescription(newRentalDTO.getDescription());
        
        //boilerplate di **** perchè java si lagna SEMPRE
        cityDTO _cityDTO = new cityDTO();
        _cityDTO.setName(newRentalDTO.getCity().getCityName());
        
        //logistica
        newRental.setCity(city.getCityFromName(_cityDTO));
        newRental.setZipCode(newRentalDTO.getZipCode());
        newRental.setStreetName(newRentalDTO.getStreetName());
        newRental.setFloor(newRentalDTO.getFloor());
        
        //prezzi  
        newRental.setPricePerNight(newRentalDTO.getPricePerNight());
        
        //ownership
        userDTO owner = new userDTO();
        owner.setUsername(jwtUtil.extractUsername(authToken));
        newRental.setOwner(_user.fetchUserByUsername(owner));
        rentalRepo.save(newRental);
    }    
    
    public float getPrice (rentalDTO houseDTO, long daysLenght) {
        rental house = rentalRepo.findById(houseDTO.getRentalID())
                .orElseThrow( () -> new RuntimeException("Impossibile trovare l'alloggio richiesto"));
        
        //bombardiro crocodilo
        return (house.getPricePerNight() * daysLenght) + (house.getCity().getOvernightStayTax() * daysLenght);
    }
    
    public rental getRentalInfo(rentalDTO lookedUpRental) {
        return rentalRepo.findById(lookedUpRental.getRentalID())
                .orElseThrow(() -> new RuntimeException("Impossibile trovare questa abitazione!"));
    }
    
    public boolean checkoutRental (rentalDTO _rental, String authToken, dateRangeDTO _dates, creditCardDTO _card) {
        userDTO guest = new userDTO();
        guest.setUsername(jwtUtil.extractUsername(authToken));
        if (_bookingService.isRentalAvabile(_dates, _rental.getRentalID())) {
            //check cc
            if (CreditCardSafety.luhnAlgo(_card)) {
                //ok, lets go
                bookingDTO _bookingDTO = new bookingDTO();
                _bookingDTO.setCheckIn(_dates.getFrom());
                _bookingDTO.setCheckOut(_dates.getTo());
                _bookingDTO.setGuest(_user.fetchUserByUsername(guest));
                _bookingDTO.setStatus(BookingStatus.CONFIRMED);
                _bookingDTO.setRental(rentalRepo.findByRentalID(_rental.getRentalID()).orElseThrow(() -> new RuntimeException("errore nel trovare la proprietà")));
                return _bookingService.bookRental(_bookingDTO);
                
            } else {
                throw new RuntimeException("Errore nella prenotazione: carta di credito falsa/invalida!");
            }
        } else {
            throw new RuntimeException("Errore nella prenotazione: Data non disponibile!");
        }
    }
    
    public rental getRentalFromID(long id) {
        return rentalRepo.findByRentalID(id).orElseThrow( () -> new RuntimeException("Non è stata trovata la proprietà!"));
    }
    
    public rental getRentalFromUserID(long userID) {
        return rentalRepo.findById(userID).orElseThrow( () -> new RuntimeException("Non è stata trovata la proprietà!"));
    }
    
    public rental getRentalFromUserIDAndRentalID(long userID, long rentalID) {
        return rentalRepo.findByRentalIDAndUser(userID, rentalID).orElseThrow( () -> new RuntimeException("Non è stata trovata la proprietà!"));
    }
    
    public List<rental> getRentalsAdvancedFilters (advancedSearchDTO as) {
        //i love boilerplate dio quel cane
        cityDTO lookedUpCityDTO = new cityDTO();
        lookedUpCityDTO.setName(as.getCityName());
        //porcoddio
        return rentalRepo.findbyAdvancedFilters(city.getCityFromName(lookedUpCityDTO).getCityID(), as.getMinPrice(), as.getMaxPrice(), as.getFrom(), as.getTo());
    }
    
    public long getLastRentalSubmittedByUser(String authToken) {
        userDTO _userDTO = new userDTO();
        _userDTO.setUsername(jwtUtil.extractUsername(authToken));
        user userObj = _user.fetchUserByUsername(_userDTO);
        return rentalRepo.getLastRentalSubmittedByUser(userObj.getId());
    }
    
    public List<rental> getAllRentalsForUser(String authToken) {
        userDTO _userDTO = new userDTO();
        _userDTO.setUsername(jwtUtil.extractUsername(authToken));
        user userObj = _user.fetchUserByUsername(_userDTO);
        return rentalRepo.getAllRentalsForUser(userObj.getId());
    }
    
    public void editRental (String authToken, rentalDTO dto) {
        userDTO _userDTO = new userDTO();
        _userDTO.setUsername(jwtUtil.extractUsername(authToken));
        user userObj = _user.fetchUserByUsername(_userDTO);
        rentalRepo.editRental(userObj.getId(), dto.getRentalID(), dto.getListingName(),  dto.getDescription(), dto.getPricePerNight());
    }
    
    public void deleteRental (String authToken, long rentalId) {
        userDTO _userDTO = new userDTO();
        _userDTO.setUsername(jwtUtil.extractUsername(authToken));
        user userObj = _user.fetchUserByUsername(_userDTO);
        rentalRepo.editRental(userObj.getId(), rentalId);
    }
    
    public List<rental> getPopularRentals(int c) {
        return rentalRepo.blissExperiencee_getTopRentalsMostBooked(c);
    }
}
