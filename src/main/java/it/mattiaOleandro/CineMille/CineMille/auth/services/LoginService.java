package it.mattiaOleandro.CineMille.CineMille.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import it.mattiaOleandro.CineMille.CineMille.auth.entities.LoginDTO;
import it.mattiaOleandro.CineMille.CineMille.auth.entities.LoginRTO;
import it.mattiaOleandro.CineMille.CineMille.user.entities.User;
import it.mattiaOleandro.CineMille.CineMille.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class LoginService {

    // stringa SECRET(andrebbe nello YAML) mi permette si "firmare" il JWT
    // https://www.uuidgenerator.net/ per generarlo
    public static final String JWT_SECRET = "95e600dc-a375-4d63-af75-66ba07d75f72";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public LoginRTO login(LoginDTO loginDTO){

        if(loginDTO == null) return null;

        User userFromDB = userRepository.findByEmail(loginDTO.getEmail());

        if(userFromDB == null || !userFromDB.isActive()) return null;

        boolean canLogin = this.canUserLogin(userFromDB, loginDTO.getPassword());
        if(!canLogin) return null;

        String JWT = generateJWT(userFromDB);

        LoginRTO out = new LoginRTO();
        out.setJWT(JWT);
        out.setUser(userFromDB);

        return out;
    }

    public boolean canUserLogin(User user, String password){

        return passwordEncoder.matches(password, user.getPassword());
    }

    static Date convertToDateViaInstant(LocalDateTime dateToConvert) {

        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static String getJWT(User user){

        Date expiresAt = convertToDateViaInstant(LocalDateTime.now().plusDays(15));

        //https://mkyong.com/java8/java-8-how-to-convert-a-stream-to-array/
        //prendiamo tutti i ruoli dell'utente e li convertiamo in array e li integriamo nel JWT Token
        String[] roles = user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new);
        //creiamo la nostra stringa JWT(JSON Web Tokens) che verrà poi assegnata all'utente
        return JWT.create()
                .withIssuer("coding-challenge-lascaux")
                .withIssuedAt(new Date())
                .withExpiresAt(expiresAt)
                .withClaim("roles",String.join(",",roles))
                .withClaim("id", user.getId())
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

    public String generateJWT(User user) {

        String JWT = getJWT(user);

        user.setJwtCreatedOn(LocalDateTime.now());
        userRepository.save(user);

        return JWT;
    }
}
