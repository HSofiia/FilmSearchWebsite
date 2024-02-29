package be.kdg.film_project.controller.api;

import be.kdg.film_project.controller.api.dto.ActorDto;
import be.kdg.film_project.controller.api.dto.FilmDto;
import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.domain.FilmCasting;
import be.kdg.film_project.service.FilmService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
public class FilmsController {
    private final FilmService service;

    public FilmsController(FilmService service) {
        this.service = service;
    }

    // "/api/films/{id}"
    @GetMapping("{id}")
    ResponseEntity<FilmDto> getOneFilm(@PathVariable("id") int filmId) {
        var film = service.getFilm(filmId);
        if (film == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(
                new FilmDto(
                        film.getFilmName(),
                        film.getYear(),
                        film.getBoxOffice(),
                        film.getGenre(),
                        film.getId()
                ));
    }

    // "/api/actors/1/films"
    @GetMapping("{id}/actors")
    ResponseEntity<List<ActorDto>> getActorsOfFilm(@PathVariable("id") int filmId) {
        var film = service.getFilmWithActors(filmId);
        if (film == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (film.getCastings().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(film.getCastings()
                .stream()
                .map(FilmCasting::getActor)
                .map(actor -> new ActorDto(
                        actor.getActorName(),
                        actor.getGender(),
                        actor.getNationality(),
                        actor.getId()
                )).toList());
    }

    // "/api/actors"
    @GetMapping
    ResponseEntity<List<FilmDto>> searchFilm(@RequestParam(required = false, value = "actorsName") String actorsName) {
        if (actorsName == null) {
            return ResponseEntity
                    .ok(service.getFilms()
                            .stream()
                            .map(film -> new FilmDto(
                                    film.getFilmName(),
                                    film.getYear(),
                                    film.getBoxOffice(),
                                    film.getGenre(),
                                    film.getId()))
                            .toList());
        } else {
            var searchResult = service.getByActors(actorsName);
            if (searchResult.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.ok(searchResult
                        .stream()
                        .map(film -> new FilmDto(
                                film.getFilmName(),
                                film.getYear(),
                                film.getBoxOffice(),
                                film.getGenre(),
                                film.getId()))
                        .toList());
            }
        }
    }

    // "/api/actors/{id}"
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteFilm(@PathVariable("id") int filmId) {
        if (service.deleteFilm(filmId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
