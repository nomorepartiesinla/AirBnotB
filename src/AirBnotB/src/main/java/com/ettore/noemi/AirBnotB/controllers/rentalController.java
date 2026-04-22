/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.controllers;

import com.ettore.noemi.AirBnotB.DTOs.advancedSearchDTO;
import com.ettore.noemi.AirBnotB.DTOs.cityDTO;
import com.ettore.noemi.AirBnotB.DTOs.creditCardDTO;
import com.ettore.noemi.AirBnotB.DTOs.dateRangeDTO;
import com.ettore.noemi.AirBnotB.DTOs.purchaseStayDTO;
import com.ettore.noemi.AirBnotB.DTOs.rentalDTO;
import com.ettore.noemi.AirBnotB.models.rental;
import com.ettore.noemi.AirBnotB.repositories.imageRepo;
import com.ettore.noemi.AirBnotB.services.bookingService;
import com.ettore.noemi.AirBnotB.services.cityService;
import com.ettore.noemi.AirBnotB.services.rentalService;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ettore
 */

@RestController
@RequestMapping("/api/rentals")
public class rentalController {
    @Autowired
    cityService city;
    @Autowired
    rentalService rental;
    @Autowired
    bookingService booking;
    @Autowired
    imageRepo imgRepo;
    
    
    private static final String UPLOAD_DIR = "uploads/"; // cartella per le foto delel case

    
    @PostMapping("lookupCities") 
    public ResponseEntity<List<rental>> lookupCities( @RequestBody cityDTO _city) {
        try {
            //praticamente in sto bordello di boilerplate:
            //manda al service che ha dentro la logica di business (quindi hash password), che manda al repository
            //però, *** ***** anche il DTO e user hanno dei check, è solo che java è pattumiera
            List<rental> rentals = city.getRentalsByCityName(_city);
            return new ResponseEntity<>(rentals, HttpStatus.OK);
        } catch (RuntimeException e) {
            return null;
        }
    }
    
    @PostMapping("advancedSearch") 
    public ResponseEntity<List<rental>> lookupCitiesAdvancedFilters(@Valid @RequestBody advancedSearchDTO _asDTO) {
        try {
            
            List<rental> rentals = rental.getRentalsAdvancedFilters(_asDTO);
            return new ResponseEntity<>(rentals, HttpStatus.OK);
        } catch (RuntimeException e) {
            return null;
        }
    }
    
    @PostMapping("insertRental")
    public ResponseEntity<String> insertRental( @RequestBody rentalDTO _rental,
        @CookieValue(value = "authToken", required = false) String jwtToken) {

        if (jwtToken == null || jwtToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Auth token missing");
        }
        try {
            rental.createRental(_rental, jwtToken);
            return ResponseEntity.ok("Aggiunta proprietà con successo!");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    
    @GetMapping("/{id}/availability")
    public ResponseEntity<?> getAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(
            Map.of(
                "rentalId", id,
                "bookedRanges", booking.getBookedRanges(id)
            )
        );
    }
    @GetMapping("/{id}/{days}/checkPrice")
    public ResponseEntity<?> getStayPrice(@PathVariable long id, @PathVariable long days) {
        rentalDTO requestedHouse = new rentalDTO();
        requestedHouse.setRentalID(id);
        return ResponseEntity.ok(
                //templating *** **** .. ok java
                Map.of("rentalId", id,
                        "price", rental.getPrice(requestedHouse, days))
        );
    }
    
    //dettagli della casa (per la pagina specifica della casa)
    @GetMapping("/{id}/details")
    public ResponseEntity<?> getStayDetails(@PathVariable long id) {
        rentalDTO requestedHouse = new rentalDTO();
        requestedHouse.setRentalID(id);
        return ResponseEntity.ok(
                rental.getRentalInfo(requestedHouse)
        );
    }
    
    @PostMapping("/buyStay")
    public ResponseEntity<String> checkOutRental
        (@Valid @RequestBody purchaseStayDTO _stayDTO,
            @CookieValue(value = "authToken", required = false) String jwtToken) //*** *****
    {
            if (jwtToken == null || jwtToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Auth token missing");
            }
            try {
                rental.checkoutRental(_stayDTO.getRental(), jwtToken, _stayDTO.getDate(), _stayDTO.getCc());
                return ResponseEntity.ok("Aggiunta prenotazione!");
            } catch (RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            }
    }
     
    @GetMapping("{searchTerm}/suggestCity")
    public ResponseEntity<List<String>> getSuggestedCities (@PathVariable String searchTerm) {
        return ResponseEntity.ok(city.getCitiesByIncompleteSearchTerm(searchTerm));
    }
    
    @GetMapping("{id}/images")
    public ResponseEntity<List<String>> getImages(@PathVariable long id) {
        List<String> imagesUrl = imgRepo.findByRentalId(id);
        List<String> urls = new ArrayList<String>();

        for (String img : imagesUrl) {
            urls.add("/uploads/" + img);
        }

        return ResponseEntity.ok(urls);
    }
    
    @GetMapping("/getLastSubmittedRental")
    public ResponseEntity<?> getLastSubmittedRental(@CookieValue(value = "authToken", required = false) String jwtToken) {
        return ResponseEntity.ok(
                Map.of("lastRentalId", rental.getLastRentalSubmittedByUser(jwtToken))
        );
    }
    
    
    //edit dei dati :((((
    
    @GetMapping("/getAllRentalsForUser")
    public ResponseEntity<?> getAllRentalsForUser(@CookieValue(value = "authToken", required = false) String jwtToken) {
        return ResponseEntity.ok(
                rental.getAllRentalsForUser(jwtToken)
        );
    }
    
    @PostMapping("/editHouseInfo") 
    public ResponseEntity<?> editHouseInfo(@CookieValue(value = "authToken", required = false) String jwtToken, @RequestBody rentalDTO dto) {
        rental.editRental(jwtToken, dto);
        return ResponseEntity.ok("ok");
    }
    
    @GetMapping("/{rentalID}/deleteRental")
    public ResponseEntity<?> deleteHouse(@CookieValue(value = "authToken", required = false) String jwtToken, @PathVariable long rentalID) {
        rental.deleteRental(jwtToken, rentalID);
        return ResponseEntity.ok("ok");
    }
    @GetMapping("/{bound}/getPopularRentals")
    public ResponseEntity<?> deleteHouse(@PathVariable int bound) {
        return ResponseEntity.ok(rental.getPopularRentals(bound));
    }
}
