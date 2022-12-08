package it.mattiaOleandro.CineMille.CineMille.auth.controllers;

import it.mattiaOleandro.CineMille.CineMille.auth.entities.RequestPasswordDTO;
import it.mattiaOleandro.CineMille.CineMille.auth.entities.RestorePasswordDTO;
import it.mattiaOleandro.CineMille.CineMille.auth.services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/password")
public class PasswordRestoreController {

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/request")
    public void passwordRequest(@RequestBody RequestPasswordDTO requestPasswordDTO) throws Exception {

        try {
            passwordService.request(requestPasswordDTO);
            if (requestPasswordDTO == null) throw new Exception("Password is null");
        }catch (Exception e){
            e.getMessage();
        }
    }

    @PostMapping("/restore")
    public void passwordRestore(@RequestBody RestorePasswordDTO restorePasswordDTO) throws Exception{

        if (restorePasswordDTO == null) throw new NullPointerException("restorePasswordDTO is null.");
        passwordService.restore(restorePasswordDTO);
    }
}