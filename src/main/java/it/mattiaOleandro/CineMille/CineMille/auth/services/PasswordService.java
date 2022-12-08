package it.mattiaOleandro.CineMille.CineMille.auth.services;

import it.mattiaOleandro.CineMille.CineMille.auth.entities.RequestPasswordDTO;
import it.mattiaOleandro.CineMille.CineMille.auth.entities.RestorePasswordDTO;
import it.mattiaOleandro.CineMille.CineMille.notification.MailNotificationService;
import it.mattiaOleandro.CineMille.CineMille.user.entities.User;
import it.mattiaOleandro.CineMille.CineMille.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User request(RequestPasswordDTO requestPasswordDTO) throws Exception {

        // verifico se sul DB è presente un user con una certa email
        User userFromDB = userRepository.findByEmail(requestPasswordDTO.getEmail());
        // se user è uguale a null o non è attivo, lancio un'eccezione
        if(userFromDB == null || !userFromDB.isActive()) throw new Exception("Cannot find user");
        //assegno un codice temporaneo
        userFromDB.setPasswordResetCode(UUID.randomUUID().toString());
        //invio il codice appena generato via email
        mailNotificationService.sendPasswordResetMail(userFromDB);
        //salvo user
        return userRepository.save(userFromDB);
    }

    public User restore(RestorePasswordDTO restorePasswordDTO) throws Exception{

        //verifico se sul DB è presente un user con un determinato PasswordResetCode
        User userFromDB = userRepository.findByPasswordResetCode(restorePasswordDTO.getResetPasswordCode());
        // se non è presente lancio un'eccezione
        if(userFromDB == null) throw new Exception("Cannot find user");

        userFromDB.setPassword(passwordEncoder.encode(restorePasswordDTO.getNewPassword()));
        userFromDB.setPasswordResetCode(null);

        //activate the user
        userFromDB.setActive(true);
        //clean ActivationCode field
        userFromDB.setActivationCode(null);

        return userRepository.save(userFromDB);
    }
}
