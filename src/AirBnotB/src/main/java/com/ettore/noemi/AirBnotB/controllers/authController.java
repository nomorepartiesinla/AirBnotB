/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.controllers;

/**
 *
 * @author ettore
 */
import com.ettore.noemi.AirBnotB.DTOs.userDTO;
import com.ettore.noemi.AirBnotB.models.user;
import com.ettore.noemi.AirBnotB.authUtils.JwtUtil;
import com.ettore.noemi.AirBnotB.services.userService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class authController {
    
    @Autowired
    private userService UserService;
    @Autowired
    private JwtUtil jwtUtil;
    //registrazione
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody userDTO UserDTO) {
        try {
            //praticamente in sto bordello di boilerplate:
            //manda al service che ha dentro la logica di business (quindi hash password), che manda al repository
            //però, *** **** anche il DTO e user hanno dei check, è solo che java è pattumiera
            UserService.registerUser(UserDTO);
            return new ResponseEntity<>("Registrazione andata a buon fine!", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    //login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody userDTO userDTO, HttpServletResponse response) {
        try {
            //il service darebbe già un eccezzione
            user existingUser = UserService.fetchUserByUsername(userDTO);
            //compare degli hash
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (existingUser != null && passwordEncoder.matches(userDTO.getPassword(), existingUser.getPasswordHash())) {
                //casino devastante, spiego il mio problema mentale su authUtils.JwtUtil
                String token = jwtUtil.generateToken(existingUser.getUsername());
                //invio token, vuole "bearer" all'inizio per convenzione, è tipo come le lire che hanno scritto "portatore pagabile a vista"
                // Create the HttpOnly cookie
                ResponseCookie cookie = ResponseCookie.from("authToken", token)
                        .httpOnly(false)        // js ok x mantenere stato login, tanto è signed
                        .secure(false)          // siamo #stirati
                        .path("/")             // validità sitewide
                        .maxAge(24 * 60 * 60)  // expiration = 1gg
                        .build();
                return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body("Login effettuato!");
            } else {
                return ResponseEntity.status(401).body("Password errata!");
            }
        } catch(RuntimeException ex) {
            return ResponseEntity.status(401).body("Impossibile trovare l'utente!");
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseCookie cookie = ResponseCookie.from("authToken", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged out");
    }

    
}
