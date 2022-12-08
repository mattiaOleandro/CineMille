package it.mattiaOleandro.CineMille.CineMille.notification;

import it.mattiaOleandro.CineMille.CineMille.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailNotificationService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendActivationEmail(User user) {

        SimpleMailMessage sms = new SimpleMailMessage();
        sms.setTo(user.getEmail()); //setto l'email dell'utente a cui voglio inviare
        sms.setSubject("Welcome to CineMille!");
        sms.setFrom("develhopeemailexercise@gmail.com");
        sms.setReplyTo("develhopeemailexercise@gmail.com");
        sms.setText("The activation code is: " + user.getActivationCode());
        //sms.setText("Clicca quì per attivare http://localhost:8080/auth/signup/activation" + user.getActivationCode());
        emailSender.send(sms);
    }

    public void sendPasswordResetMail(User user) {

        SimpleMailMessage sms = new SimpleMailMessage();
        sms.setTo(user.getEmail());
        sms.setSubject("Password recovery service");
        sms.setFrom("develhopeemailexercise@gmail.com");
        sms.setReplyTo("develhopeemailexercise@gmail.com");
        sms.setText("The password reset code is: " + user.getPasswordResetCode());
        //sms.setText("Clicca quì per resettare http://localhost:8080/auth/signup/activation" + user.getActivationCode());
        emailSender.send(sms);
    }
}
