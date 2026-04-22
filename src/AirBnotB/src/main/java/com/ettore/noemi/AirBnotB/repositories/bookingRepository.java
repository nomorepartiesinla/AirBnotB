/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.repositories;

import com.ettore.noemi.AirBnotB.models.booking;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author ettore
 */
public interface bookingRepository extends JpaRepository<booking, Long> {

    @Query("""
       SELECT b FROM booking b
       WHERE b.rental.id = :rental_id
    """)
    List<booking> findConfirmedByRentalId(@Param("rental_id") Long rentalId);
    
    @Query("""
       SELECT COUNT(b) > 0 FROM booking b
       WHERE b.rental.id = :rentalId
       AND b.status = 'CONFIRMED'
       AND b.checkIn < :checkOut
       AND b.checkOut > :checkIn
    """)
    boolean existsOverlappingBooking(
        @Param("rentalId") Long rentalId,
        @Param("checkIn") LocalDate checkIn,
        @Param("checkOut") LocalDate checkOut
    );
}

