package com.ettore.noemi.AirBnotB.services;

import com.ettore.noemi.AirBnotB.DTOs.rentalRatingDTO;
import com.ettore.noemi.AirBnotB.DTOs.userDTO;
import com.ettore.noemi.AirBnotB.authUtils.JwtUtil;
import com.ettore.noemi.AirBnotB.models.rental;
import com.ettore.noemi.AirBnotB.models.rentalRating;
import com.ettore.noemi.AirBnotB.models.user;
import com.ettore.noemi.AirBnotB.repositories.rentalRatingRepository;
import com.ettore.noemi.AirBnotB.repositories.rentalRepository;
import com.ettore.noemi.AirBnotB.repositories.userRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class rentalRatingService {
    
    @Autowired
    private rentalRatingRepository ratingRepository;
    @Autowired
    private rentalRepository RentalRepository;
    @Autowired
    private userRepository UserRepository;
    @Autowired
    private JwtUtil jwtUtil;

    // ==========================
    // CREATE
    // ==========================
    @Transactional
    public rentalRating createRating(String authToken, rentalRatingDTO dto) {
        userDTO _userDTO = new userDTO();
        _userDTO.setUsername(jwtUtil.extractUsername(authToken));
        user userToFetch = UserRepository.findByUsername(_userDTO.getUsername()).orElseThrow(); //qui si muore da uomini
        // check doppie recensioni
        ratingRepository
                .findByEvaluatedRental_RentalIDAndRatingUser_Id(
                        dto.getRentalID(),
                        userToFetch.getId()
                )
                .ifPresent(r -> {
                    throw new IllegalStateException(
                            "Hai già recensito questo annuncio"
                    );
                });

        rental rental = RentalRepository.findById(dto.getRentalID())
                .orElseThrow(() -> new IllegalArgumentException("Rental non trovato"));

        user user = UserRepository.findById(userToFetch.getId())
                .orElseThrow(() -> new IllegalArgumentException("User non trovato"));

        rentalRating rating = new rentalRating();
        rating.setEvaluatedRental(rental);
        rating.setRatingUser(userToFetch);
        rating.setEvaluation(dto.getEvaluation());
        rating.setStars(dto.getStars());

        return ratingRepository.save(rating);
    }
    
    public int getAvgRating (long rentalID)  {
        return ratingRepository.getAvgRatingForRental(rentalID);
    }
    
    public List<rentalRating> fetchByPropId (long rId) {
        return ratingRepository.findByEvaluatedRental_RentalID(rId);
    }
}
