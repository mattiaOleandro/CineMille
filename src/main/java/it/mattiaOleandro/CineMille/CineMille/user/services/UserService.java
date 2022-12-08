package it.mattiaOleandro.CineMille.CineMille.user.services;

import it.mattiaOleandro.CineMille.CineMille.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
}
