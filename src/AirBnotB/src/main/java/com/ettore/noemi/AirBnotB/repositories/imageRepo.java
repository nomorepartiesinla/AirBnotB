/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.repositories;

import com.ettore.noemi.AirBnotB.models.image;
import com.ettore.noemi.AirBnotB.models.rental;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author ettore
 */
public interface imageRepo extends JpaRepository<image, Long> {
   List<image> findByRental(rental rental);

    @Query(value="""
                SELECT path from image
                WHERE rental_id = :id
           """, nativeQuery = true)
    public List<String> findByRentalId(long id);
}
