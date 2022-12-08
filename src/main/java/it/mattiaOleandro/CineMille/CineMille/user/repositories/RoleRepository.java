package it.mattiaOleandro.CineMille.CineMille.user.repositories;

import it.mattiaOleandro.CineMille.CineMille.user.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
