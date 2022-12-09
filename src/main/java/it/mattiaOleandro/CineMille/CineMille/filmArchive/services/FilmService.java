package it.mattiaOleandro.CineMille.CineMille.filmArchive.services;

import it.mattiaOleandro.CineMille.CineMille.filmArchive.entities.Film;
import it.mattiaOleandro.CineMille.CineMille.filmArchive.repositories.FilmRepository;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@Service
@Data
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    public List<Film> upcoming(){

        List<Film> listOfFilmUpcoming = new ArrayList<>();

        List<Date> listOfDateFilmsEnd = filmRepository.findAllEndDateFilms();

        LocalDate today = LocalDate.now();

        List<Date> listOfDate = new ArrayList<>();

        for (int i = 0; i < listOfDateFilmsEnd.size(); i++) {
            Date dateFilmEnd = listOfDateFilmsEnd.get(i);
            if(today.isBefore(dateFilmEnd.toLocalDate())){
                listOfDate.add(dateFilmEnd);
            }
            for (int j = 0; j < listOfDate.size(); j++) {
                Date dateFilm = listOfDate.get(j);
                List<Film> films = filmRepository.findAllFilmsUpcoming(dateFilm);
                for (int k = 0; k < films.size(); k++) {
                    Film film = films.get(k);
                    listOfFilmUpcoming.add(film);
                }
            }
        }
        return listOfFilmUpcoming;
    }
}
