package it.mattiaOleandro.CineMille.CineMille.auth.controllers;

import it.mattiaOleandro.CineMille.CineMille.auth.entities.SignupActivationDTO;
import it.mattiaOleandro.CineMille.CineMille.auth.entities.SignupDTO;
import it.mattiaOleandro.CineMille.CineMille.auth.entities.SignupManagerDTO;
import it.mattiaOleandro.CineMille.CineMille.auth.services.SignupService;
import it.mattiaOleandro.CineMille.CineMille.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SignupController {

    @Autowired
    private SignupService signupService;

    @PostMapping("/signup")
    public User signup(@RequestBody SignupDTO signupDTO) throws Exception {

        if (signupDTO == null) throw new NullPointerException("signupDTO is null");
        return signupService.signup(signupDTO);
    }

    @PostMapping("/signup/manager/{role}")
    public User signupManager(@RequestBody SignupManagerDTO signupManagerDTO, @PathVariable String role) throws Exception {

        if (signupManagerDTO == null) throw new NullPointerException("signupManagerDTO is null");
        if (role == null) throw new NullPointerException("role is null");
        return signupService.signupManager(signupManagerDTO, role);
    }

    @PostMapping("/signup/{role}")
    public User signup(@RequestBody SignupDTO signupDTO, @PathVariable String role) throws Exception {

        if (signupDTO == null) throw new NullPointerException("signupDTO is null");
        if (role == null) throw new NullPointerException("role is null");
        return signupService.signup(signupDTO, role);
    }

    @PostMapping("/signup/activation")
    public User signup(@RequestBody SignupActivationDTO signupActivationDTO) throws Exception {

        if (signupActivationDTO == null) throw new NullPointerException("signupActivationDTO is null");
        return signupService.activate(signupActivationDTO);
    }
}
