package com.ettore.noemi.AirBnotB.repositories;

import com.ettore.noemi.AirBnotB.models.rentalRating;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface rentalRatingRepository extends JpaRepository<rentalRating, Long> {

    Optional<rentalRating> findByEvaluatedRental_RentalIDAndRatingUser_Id(Long rentalId, Long userId);
    
    @Query(value="""
                      SELECT AVG(stars)
                      FROM rental_rating
                      WHERE rental_id = :rental;
           """, nativeQuery=true)
    
    int getAvgRatingForRental(long rental);
    
    List<rentalRating> findByEvaluatedRental_RentalID(long rentalId);
}
