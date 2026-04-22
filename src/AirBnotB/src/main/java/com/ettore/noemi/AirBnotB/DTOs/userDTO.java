/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.DTOs;

/**
 *
 * @author ettore
 */

import jakarta.validation.constraints.*;

public class userDTO {

    @NotEmpty(message = "Errore: lo username è vuoto")
    @Size(min = 5, max = 32, message = "Lo username dev'essere di almeno 5 caratteri ma non più di 32")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9_-]{2,18}[a-zA-Z0-9]$", message = "Lo username dev'essere di almeno 5 caratteri ma non più di 32; Inoltre, può contenere trattini, underscore e anche numeri!")
    private String username;

    @NotEmpty(message = "Errore: password vuota")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password di minimo 8 caratteri, con almeno un simbolo, una maiuscola, una minuscola e un numero es: Mari0!123")
    private String password;

    @Email(message = "Email invalida (come te)")
    @NotEmpty(message = "Senza email non ti puoi registrare")
    private String email;

    @NotEmpty(message = "Errore: il nome è vuoto")
    @Size(min = 3, max = 32, message = "il nome non ha dimensione valida")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9_-]{2,18}[a-zA-Z0-9]$", message = "nome invalido")
    private String name;
   
    @NotEmpty(message = "Errore: il cognome è vuoto")
    @Size(min = 3, max = 32, message = "il cognome non ha dimensione valida")
    @Pattern(regexp = "^[a-zA-Z0-9][a-zA-Z0-9_-]{2,18}[a-zA-Z0-9]$", message = "cognome invalido")
    private String surname;
    
    // Getters & Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    
    
}

