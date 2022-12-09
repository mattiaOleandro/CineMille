package it.mattiaOleandro.CineMille.CineMille.auth.services;

import it.mattiaOleandro.CineMille.CineMille.auth.entities.SignupActivationDTO;
import it.mattiaOleandro.CineMille.CineMille.auth.entities.SignupDTO;
import it.mattiaOleandro.CineMille.CineMille.auth.entities.SignupManagerDTO;
import it.mattiaOleandro.CineMille.CineMille.notification.MailNotificationService;
import it.mattiaOleandro.CineMille.CineMille.user.entities.Role;
import it.mattiaOleandro.CineMille.CineMille.user.entities.User;
import it.mattiaOleandro.CineMille.CineMille.user.repositories.RoleRepository;
import it.mattiaOleandro.CineMille.CineMille.user.repositories.UserRepository;
import it.mattiaOleandro.CineMille.CineMille.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class SignupService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MailNotificationService mailNotificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User signup(SignupDTO signupDTO) throws Exception {

        return this.signup(signupDTO, Roles.REGISTERED);
    }

    public User signup(SignupDTO signupDTO, String role) throws Exception {

        User userInDB = userRepository.findByEmail(signupDTO.getEmail());
        if(userInDB != null) throw new Exception("User already exist");

        User user = new User();
        user.setName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setSurname(signupDTO.getSurname());
        //la password viene codificata con PasswordEncoder e assegnata all'utente
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

        user.setActivationCode(UUID.randomUUID().toString());

        //assegniamo all'utente un set di ruoli
        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName(role.toUpperCase());
        if(!userRole.isPresent()) throw new Exception("Cannot set user role");
        roles.add(userRole.get());
        user.setRoles(roles);

        //mailNotificationService.sendActivationEmail(user);
        return userRepository.save(user);
    }

    public User signupManager(SignupManagerDTO signupManagerDTO, String role) throws Exception{

        User userInDB = userRepository.findByEmail(signupManagerDTO.getEmail());
        if(userInDB != null) throw new Exception("Manager already exist");

        User user = new User();
        user.setName(signupManagerDTO.getName());
        user.setEmail(signupManagerDTO.getEmail());
        user.setSurname(signupManagerDTO.getSurname());
        user.setBadgeNumber(signupManagerDTO.getBadgeNumber());
        user.setDocumentNumber(signupManagerDTO.getDocumentNumber());
        user.setPassword(passwordEncoder.encode(signupManagerDTO.getPassword()));

        user.setActivationCode(UUID.randomUUID().toString());

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole =roleRepository.findByName(role.toUpperCase());
        if(!userRole.isPresent()) throw new Exception("Cannot set Manager role");

        roles.add(userRole.get());
        user.setRoles(roles);

        //mailNotificationService.sendActivationEmail(user);
        return userRepository.save(user);
    }

    public User activate(SignupActivationDTO signupActivationDTO) throws Exception {

        User user = userRepository.findByActivationCode(signupActivationDTO.getActivationCode());
        if(user == null) throw  new Exception("User not found");
        user.setActive(true);
        user.setActivationCode(null);
        return userRepository.save(user);
    }
}
