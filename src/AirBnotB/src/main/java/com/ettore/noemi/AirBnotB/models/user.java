/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ettore.noemi.AirBnotB.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
//dependency hell per la validazione, ok java
import jakarta.validation.constraints.*;
//Per mappare i listing e le prenotazioni agli utenti (uno a molti (se hai i soldi))
import java.util.List;

@Entity
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotEmpty(message = "Errore: lo username è vuoto")
    @Size(min = 5, max = 32, message = "Lo username dev'essere di almeno 5 caratteri ma non più di 32 che gira"
            + "tutto sul pentium qui")
    private String username;

    @NotEmpty(message = "Senza una password difficilmente ci entri")
    @JsonIgnore                     //evitiamo di leakare i dati (si, su una request leakavo tutto :dead:)
    private String passwordHash;

    @Email(message = "La mail dev'essere corretta, apriti una gmail per l'amor di dio")
    @NotEmpty(message = "Senza una mail col cazzo che ti iscrivi")
    @JsonIgnore
    private String email;
    
    @NotEmpty
    @Size(min = 3, max = 32, message="come fai ad avere un nome così lungo o così corto?")
    private String name;
   
    @NotEmpty
    @Size(min = 2, max = 32, message="come fai ad avere un cognome così lungo o così corto?")
    private String surname;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<rental> rentals;
    

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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
