/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.DTOs;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ettore
 */
public class imageDTO {
    @NotNull(message = "è richiesto l'id della casa")
    private Long rentalId;

    @NotNull(message = "è necessario mettere l'immagine")
    private MultipartFile file;

    // Getters / Setters
    public Long getRentalId() { return rentalId; }
    public void setRentalId(Long rentalId) { this.rentalId = rentalId; }

    public MultipartFile getFile() { return file; }
    public void setFile(MultipartFile file) { this.file = file; }
}
