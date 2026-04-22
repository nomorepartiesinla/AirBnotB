/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.services;

import com.ettore.noemi.AirBnotB.DTOs.cityDTO;
import com.ettore.noemi.AirBnotB.models.cities;
import com.ettore.noemi.AirBnotB.models.rental;
import com.ettore.noemi.AirBnotB.repositories.cityRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ettore
 */
@Service
@Transactional //sennò il lazyloading non carica i rental associati, ***** ***** java è una presa per il culo
public class cityService {
    @Autowired
    cityRepository cityRepo;
    
    public List<rental> getRentalsByCityName (cityDTO city) {
        return cityRepo.findRentalsByCityName(city.getName());
    }
    
    public cities getCityFromName (cityDTO _cityDTO) {
        return cityRepo.findByCityName(_cityDTO.getName())
            .orElseThrow(() -> new RuntimeException("Impossibile trovare la città"));
    }
    public List<String> getCitiesByIncompleteSearchTerm (String s) {
        return cityRepo.findCitiesByIncompleteSearchTerm(s);
    }
}
