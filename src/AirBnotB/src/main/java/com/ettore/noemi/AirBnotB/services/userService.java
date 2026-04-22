/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.services;

/**
 *
 * @author ettore
 */

import com.ettore.noemi.AirBnotB.models.user;
import com.ettore.noemi.AirBnotB.DTOs.userDTO;
import com.ettore.noemi.AirBnotB.authUtils.JwtUtil;
import com.ettore.noemi.AirBnotB.repositories.userRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userService {

    @Autowired
    private userRepository UserRepository;

    @Autowired
    private JwtUtil jwtUtil;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public user registerUser(userDTO UserDTO) {
        // Check se esiste già lo username
        if (UserRepository.findByUsername(UserDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username già preso");
        }
        // Check della mail
        if (UserRepository.findByEmail(UserDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email già registrata!");
        }

        //Registrazione del nuovo utente
        user newUser = new user();
        newUser.setUsername(UserDTO.getUsername());
        newUser.setEmail(UserDTO.getEmail());
        newUser.setPasswordHash(passwordEncoder.encode(UserDTO.getPassword()));  // Hashing
        newUser.setName(UserDTO.getName());
        newUser.setSurname(UserDTO.getSurname());

        return UserRepository.save(newUser);  //salvataggio
    }
    
    public user fetchUserByUsername(userDTO _userDTO) {
        // sto casino perchè usa optional
        //questi froci manco hanno le ***** di tirare un bel null e mandare tutto a *******...
        return UserRepository.findByUsername(_userDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Impossibile trovare l'utente!"));
    }
    
    public user updateUserPreferences(userDTO authUserDTO, userDTO partialUserDTO) {

    // utente autenticato
    user u = fetchUserByUsername(authUserDTO);

    // ---- EMAIL ----
    if (partialUserDTO.getEmail() != null && !partialUserDTO.getEmail().isBlank()) {
        if (!partialUserDTO.getEmail().equals(u.getEmail())) {
            UserRepository.findByEmail(partialUserDTO.getEmail())
                    .ifPresent(existing -> {
                        throw new RuntimeException("Email già registrata!");
                    });
            u.setEmail(partialUserDTO.getEmail());
        }
    }

    // ---- PASSWORD ----
    if (partialUserDTO.getPassword() != null && !partialUserDTO.getPassword().isBlank()) {
        u.setPasswordHash(passwordEncoder.encode(partialUserDTO.getPassword()));
    }

    // ---- NAME ----
    if (partialUserDTO.getName() != null && !partialUserDTO.getName().isBlank()) {
        u.setName(partialUserDTO.getName());
    }

    // ---- SURNAME ----
    if (partialUserDTO.getSurname() != null && !partialUserDTO.getSurname().isBlank()) {
        u.setSurname(partialUserDTO.getSurname());
    }

    return UserRepository.save(u);
}
    public String whoami (String token) {
        userDTO guest = new userDTO();
        guest.setUsername(jwtUtil.extractUsername(token));
        return guest.getUsername();
    }
    
}
