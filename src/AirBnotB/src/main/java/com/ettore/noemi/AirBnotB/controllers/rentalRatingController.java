package com.ettore.noemi.AirBnotB.controllers;

import com.ettore.noemi.AirBnotB.DTOs.rentalRatingDTO;
import com.ettore.noemi.AirBnotB.models.rentalRating;
import com.ettore.noemi.AirBnotB.services.rentalRatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
public class rentalRatingController {

    private final rentalRatingService ratingService;

    public rentalRatingController(rentalRatingService ratingService) {
        this.ratingService = ratingService;
    }

    // ==========================
    // 
    // POST /api/ratings
    // ==========================
    @PostMapping
    public ResponseEntity<rentalRating> createRating(
            @CookieValue(value = "authToken", required = false) String jwtToken,
            @RequestBody rentalRatingDTO dto
    ) {
        rentalRating rating = ratingService.createRating(jwtToken, dto);
        return ResponseEntity.ok(rating);
    }


    // ==========================
    // GET ratings by rental ID
    // GET /api/ratings/rental/{rentalId}
    // ==========================
    /*@GetMapping("/rental/{rentalId}")
    public ResponseEntity<List<rentalRating>> getRatingsByRental(
            @PathVariable Long rentalId
    ) {
        return ResponseEntity.ok(ratingService.getRatingsByRental(rentalId));
    }*/

    @GetMapping("/{propertyID}/getRating")
    public ResponseEntity<?> getRatingForHouse(
            @PathVariable long propertyID
    ) {
        return ResponseEntity.ok(Map.of("rating", ratingService.getAvgRating(propertyID)));
    }
    
    @GetMapping("/{id}/getReviewsForHouse")
    public ResponseEntity<List<rentalRating>> getReviewsForHouse (@PathVariable long id) {
        return ResponseEntity.ok(ratingService.fetchByPropId(id));
    }
}
