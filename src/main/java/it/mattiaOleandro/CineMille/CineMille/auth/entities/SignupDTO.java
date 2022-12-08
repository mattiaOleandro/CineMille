package it.mattiaOleandro.CineMille.CineMille.auth.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class SignupDTO {

    private String name;
    private String surname;

    @Column(unique = true)
    private String email;
    private String password;
}
