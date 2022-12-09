package it.mattiaOleandro.CineMille.CineMille.filmArchive.controllers;

import it.mattiaOleandro.CineMille.CineMille.filmArchive.entities.Film;
import it.mattiaOleandro.CineMille.CineMille.filmArchive.entities.FilmDTO;
import it.mattiaOleandro.CineMille.CineMille.filmArchive.repositories.FilmRepository;
import it.mattiaOleandro.CineMille.CineMille.filmArchive.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmService filmService;

    @PostMapping("/postFilm")
    public ResponseEntity postFilm(@RequestBody FilmDTO filmDTO){
        Film film = new Film();

        film.setFilmName(filmDTO.getFilmName());
        film.setDescription(filmDTO.getDescription());
        film.setFilmDateStart(filmDTO.getFilmDateStart());
        film.setFilmDateEnd(filmDTO.getFilmDateEnd());

        try {
            if(film == null) return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(filmRepository.save(film));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/upcoming")
    public ResponseEntity getUpcomingFilm(){

        try {
            return ResponseEntity.ok(filmService.upcoming());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/filterDateStart")
    public ResponseEntity getFilterFilmDateStart(@RequestBody FilmDTO filmDTO){

        try {
            return ResponseEntity.ok(filmRepository.findAllFilmsFilterDateStart(filmDTO.getFilmDateStart()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/filterDateEnd")
    public ResponseEntity getFilterFilmDateEnd(@RequestBody FilmDTO filmDTO){

        try {
            return ResponseEntity.ok(filmRepository.findAllFilmsFilterDateEnd(filmDTO.getFilmDateEnd()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/historic")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity getHistoricFilm(){

        try {
            return ResponseEntity.ok(filmRepository.findAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
