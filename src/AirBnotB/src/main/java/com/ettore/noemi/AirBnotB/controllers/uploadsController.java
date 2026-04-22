/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.controllers;

import com.ettore.noemi.AirBnotB.DTOs.userDTO;
import com.ettore.noemi.AirBnotB.authUtils.JwtUtil;
import com.ettore.noemi.AirBnotB.models.image;
import com.ettore.noemi.AirBnotB.models.rental;
import com.ettore.noemi.AirBnotB.models.user;
import com.ettore.noemi.AirBnotB.repositories.imageRepo;
import com.ettore.noemi.AirBnotB.services.rentalService;
import com.ettore.noemi.AirBnotB.services.userService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
/**
 *
 * @author ettore
 */
@RestController
@RequestMapping("/api/uploads")
public class uploadsController {
    
    @Autowired
    private imageRepo _imageRepo;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private userService _user;
    
    @Autowired
    private rentalService _rental;
            
    
    private static final java.nio.file.Path UPLOAD_ROOT = Paths.get("uploads");

    @PostMapping("{id}/image")
    public ResponseEntity<?> uploadImage(@PathVariable long id, 
            @RequestParam("file") MultipartFile file,
            @CookieValue(value = "authToken", required = false) String jwtToken)
    {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("La foto fornita è vuota");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.badRequest().body("bella giocata ma no, col cazzo!");
        }

        try {
            Files.createDirectories(UPLOAD_ROOT);
            
            //filename top
            String extension = contentType.substring(contentType.lastIndexOf('/') + 1);
            String filename = java.util.UUID.randomUUID() + "." + extension;

            java.nio.file.Path destination = UPLOAD_ROOT.resolve(filename);
            
            //SECURITY!!!!!1
            //cjeck se twin ha effettivamente quella casa o se è senzatetto
            if (jwtToken == null || jwtToken.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Auth token missing");
            }
            try {
                userDTO hostDTO = new userDTO();
                hostDTO.setUsername(jwtUtil.extractUsername(jwtToken));
                user _host = _user.fetchUserByUsername(hostDTO);
                rental foundProperty = _rental.getRentalFromUserIDAndRentalID(_host.getId().longValue(), id); //più ******** del c++
                //scrittura sul disco del pc che ho rubato ieri insieme al rame
                try (InputStream in = file.getInputStream()) {
                    Files.copy(in, destination, StandardCopyOption.REPLACE_EXISTING);
                }

                image _img = new image();

                _img.setRental(_rental.getRentalFromID(id));
                _img.setPath(filename);
                _imageRepo.save(_img);

                // mandare bomba
                return ResponseEntity.ok("upload andato a buon fine twin!");
            } catch (RuntimeException ex) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            }
            
            
            
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload fallito");
        }
    }
}
