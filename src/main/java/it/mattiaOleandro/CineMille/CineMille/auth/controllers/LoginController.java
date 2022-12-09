package it.mattiaOleandro.CineMille.CineMille.auth.controllers;

import it.mattiaOleandro.CineMille.CineMille.auth.entities.LoginDTO;
import it.mattiaOleandro.CineMille.CineMille.auth.entities.LoginRTO;
import it.mattiaOleandro.CineMille.CineMille.auth.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginRTO login(@RequestBody LoginDTO loginDTO)throws Exception{

        if (loginDTO == null) throw new NullPointerException("loginDTO is null.");
        LoginRTO loginRTO = loginService.login(loginDTO);
        if(loginRTO == null) throw new Exception("Cannot login");
        return loginRTO;
    }
}