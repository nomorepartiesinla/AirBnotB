/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.repositories;

/**
 *
 * @author ettore
 */
import com.ettore.noemi.AirBnotB.DTOs.cityDTO;
import com.ettore.noemi.AirBnotB.models.cities;
import com.ettore.noemi.AirBnotB.models.rental;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface rentalRepository extends JpaRepository<rental, Long> {
    Optional<rental> findByRentalID(long id);
    @Query(value="""
                SELECT * FROM rental r
                WHERE r.rental_id = :rental AND id = :user
           """, nativeQuery = true)
    Optional<rental> findByRentalIDAndUser(long user, long rental);
    List<rental> findByCity(cities City);
    
    @Query("""
    SELECT r
    FROM rental r
    WHERE r.city.id = :cityId
      AND r.pricePerNight BETWEEN :minPrice AND :maxPrice
      AND NOT EXISTS (
          SELECT b
          FROM booking b
          WHERE b.rental.id = r.rentalID
            AND b.status = 'CONFIRMED'
            AND b.checkIn < :checkOut
            AND b.checkOut > :checkIn
      )
    """)
    List<rental> findbyAdvancedFilters(
        @Param("cityId") Long cityId,
        @Param("minPrice") float minPrice,
        @Param("maxPrice") float maxPrice,
        @Param("checkIn") LocalDate checkIn,
        @Param("checkOut") LocalDate checkOut
    );    
    
    @Query(value = """
                SELECT rental_id
                FROM rental
                WHERE id = :userID
                ORDER BY rental_id
                LIMIT 1
           """, nativeQuery = true)
    long getLastRentalSubmittedByUser(long userID);
    
    
     @Query(value = """
                SELECT *
                FROM rental
                WHERE id = :userID
           """, nativeQuery = true)
    List<rental> getAllRentalsForUser(long userID);
    
    
    @Query(value = """
                UPDATE `rental` 
                SET 
                    description = :desc,
                    listing_name = :name,
                    price_per_night= :price
                WHERE id = :userID AND rental_id = :rentalID
           """, nativeQuery = true)
    void editRental (long userID, long rentalID, String name, String desc, float price);
    
    @Query(value = """
                DELETE FROM `rental` 
                WHERE id = :userID AND rental_id = :rentalID
           """, nativeQuery = true)
    void editRental (long userID, long rentalID);
    
    
    @Query(value = """
            SELECT r.*
            FROM rental r
            JOIN (
                SELECT rental_id, COUNT(*) AS reservation_count
                FROM booking
                GROUP BY rental_id
                ORDER BY reservation_count DESC
                LIMIT :upperBound
            ) b ON b.rental_id = r.rental_id
    """, nativeQuery = true)
    List<rental> blissExperiencee_getTopRentalsMostBooked(long upperBound);



    
    
}
