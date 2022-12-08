package it.mattiaOleandro.CineMille.CineMille.user.utils;

import it.mattiaOleandro.CineMille.CineMille.user.entities.User;

public class Roles {

    public final static String REGISTERED = "REGISTERED";
    public final static String ADMIN = "ADMIN";
    public final static String MANAGER = "MANAGER";

    public static boolean hasRole(User user, String roleInput){
        return user.getRoles().stream().filter(role -> role.getName().equals(roleInput)).count() != 0;
    }
}
