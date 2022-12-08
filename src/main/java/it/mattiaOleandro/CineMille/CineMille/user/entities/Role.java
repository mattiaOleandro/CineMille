package it.mattiaOleandro.CineMille.CineMille.user.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String description;
}