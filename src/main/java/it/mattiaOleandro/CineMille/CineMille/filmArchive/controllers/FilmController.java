package it.mattiaOleandro.CineMille.CineMille.filmArchive.controllers;

import it.mattiaOleandro.CineMille.CineMille.filmArchive.entities.Film;
import it.mattiaOleandro.CineMille.CineMille.filmArchive.entities.FilmDTO;
import it.mattiaOleandro.CineMille.CineMille.filmArchive.repositories.FilmRepository;
import it.mattiaOleandro.CineMille.CineMille.filmArchive.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmService filmService;

    //TODO risolvere problemi inerenti alle date.
    @GetMapping("/upcoming")
    public List<Film> getUpcomingFilm(){

        return filmService.upcoming();
    }

    @GetMapping("/filter")
    public List<Film> getFilterFilm(@RequestBody FilmDTO filmDTO){

        return filmRepository.findAllFilmsFilter(filmDTO.getFilmDateStart(), filmDTO.getFilmDateEnd());
    }

    @GetMapping("/historic")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public List<Film> getHistoricFilm(){

        return filmRepository.findAll();
    }
}
