/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.repositories;

/**
 *
 * @author ettore
 */



import com.ettore.noemi.AirBnotB.models.cities;
import com.ettore.noemi.AirBnotB.models.rental;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface cityRepository extends JpaRepository<cities, Long> {
    Optional<cities> findByCityName(String name);
    
    //toilet & skibbidi presentami la sister, N DI ******
    @Query("""
    SELECT r FROM rental r
    WHERE r.city.cityName = :cityName
    """)
    List<rental> findRentalsByCityName(String cityName);
    
    @Query (value="""
                SELECT c.city_name FROM cities c
                WHERE UPPER(c.city_name) LIKE UPPER(CONCAT(:searchTerm, '%'))
                LIMIT 5
            """,
            nativeQuery = true)
    List<String> findCitiesByIncompleteSearchTerm(@Param("searchTerm") String searchTerm);
}
