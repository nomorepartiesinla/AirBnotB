/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.controllers;

import com.ettore.noemi.AirBnotB.DTOs.rentalDTO;
import com.ettore.noemi.AirBnotB.DTOs.userDTO;
import com.ettore.noemi.AirBnotB.models.user;
import com.ettore.noemi.AirBnotB.services.userService;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ettore.noemi.AirBnotB.authUtils.JwtUtil;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author ettore
 */
@RestController
@RequestMapping("/api/user")
public class userController {
    @Autowired
    private userService UserService;
    @Autowired
    private JwtUtil jwtUtil;
    
    @PutMapping("/preferences")
    public user updateUserPreferences(
            @CookieValue(value = "authToken", required = false) String jwtToken,
            @RequestBody userDTO partialUserDTO) {

        if (jwtToken == null) {
            throw new RuntimeException("Token JWT mancante");
        }
        userDTO _userDTO = new userDTO();
        _userDTO.setUsername(jwtUtil.extractUsername(jwtToken));

        return UserService.updateUserPreferences(_userDTO, partialUserDTO);
    }
    @GetMapping("/whoami")
    public ResponseEntity<String> WhoAmi(@CookieValue(value = "authToken", required = false) String jwtToken) {
        return ResponseEntity.ok(UserService.whoami(jwtToken));
    }
}
