package it.mattiaOleandro.CineMille.CineMille.auth.entities;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupManagerDTO extends SignupDTO{

    @Column(unique = true)
    private String badgeNumber;

    @Column(unique = true)
    private String documentNumber;
}
