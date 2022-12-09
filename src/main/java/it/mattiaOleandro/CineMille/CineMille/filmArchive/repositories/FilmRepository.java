package it.mattiaOleandro.CineMille.CineMille.filmArchive.repositories;

import it.mattiaOleandro.CineMille.CineMille.filmArchive.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query(nativeQuery = true, value = "SELECT f.`film_date_end` FROM `film` AS f")
    List<Date> findAllEndDateFilms();

    @Query(nativeQuery = true, value = "SELECT f.* FROM `film` AS f WHERE f.`film_date_end` = :filmDataEnd")
    List<Film> findAllFilmsUpcoming(@Param(value = "filmDataEnd") Date filmDataEnd);

    @Query(nativeQuery = true, value = "SELECT f.* FROM `film` AS f " +
            "WHERE f.`film_date_start` = :filmDateStart AND f.`film_date_end` = :filmDateEnd")
    List<Film> findAllFilmsFilter(@Param(value = "filmDateStart") LocalDate filmDateStart,
                                  @Param(value = "filmDateEnd") LocalDate filmDateEnd);
}
