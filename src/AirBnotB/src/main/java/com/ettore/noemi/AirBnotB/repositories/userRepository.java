/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.repositories;

/**
 *
 * @author ettore
 */

import com.ettore.noemi.AirBnotB.models.user;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface userRepository extends JpaRepository<user, Long> {
    Optional<user> findByUsername(String username);
    Optional<user> findByEmail(String email);
}

