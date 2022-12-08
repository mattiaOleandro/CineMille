package it.mattiaOleandro.CineMille.CineMille.auth.entities;

import it.mattiaOleandro.CineMille.CineMille.user.entities.User;
import lombok.Data;

@Data
public class LoginRTO {

    private User user;
    private String JWT;
}
