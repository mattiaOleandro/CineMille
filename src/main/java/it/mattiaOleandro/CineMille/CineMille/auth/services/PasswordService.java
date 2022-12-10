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

        User userFromDB = userRepository.findByEmail(requestPasswordDTO.getEmail());
        if(userFromDB == null || !userFromDB.isActive()) throw new Exception("Cannot find user");
        userFromDB.setPasswordResetCode(UUID.randomUUID().toString());

        //invio il codice appena generato via email TODO l'email da problemi di sicurezza.
        //mailNotificationService.sendPasswordResetMail(userFromDB);

        return userRepository.save(userFromDB);
    }

    public User restore(RestorePasswordDTO restorePasswordDTO) throws Exception{

        User userFromDB = userRepository.findByPasswordResetCode(restorePasswordDTO.getResetPasswordCode());

        if(userFromDB == null) throw new Exception("Cannot find user");

        userFromDB.setPassword(passwordEncoder.encode(restorePasswordDTO.getNewPassword()));
        userFromDB.setPasswordResetCode(null);

        userFromDB.setActive(true);
        userFromDB.setActivationCode(null);

        return userRepository.save(userFromDB);
    }
}
