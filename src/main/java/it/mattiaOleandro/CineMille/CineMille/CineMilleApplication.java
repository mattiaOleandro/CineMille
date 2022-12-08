package it.mattiaOleandro.CineMille.CineMille;

import it.mattiaOleandro.CineMille.CineMille.user.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CineMilleApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CineMilleApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String[] args) throws Exception {

	}

}
