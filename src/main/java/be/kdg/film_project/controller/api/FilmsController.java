package be.kdg.film_project.controller.api;

import be.kdg.film_project.controller.api.dto.actor.ActorDto;
import be.kdg.film_project.controller.api.dto.film.FilmDto;
import be.kdg.film_project.controller.api.dto.film.NewFilmDto;
import be.kdg.film_project.controller.api.dto.film.UpdateFilmDto;
import be.kdg.film_project.domain.FilmCasting;
import be.kdg.film_project.service.FilmService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/films")
public class FilmsController {
    private final FilmService service;
    private final ModelMapper modelMapper;

    public FilmsController(FilmService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/api/addFilm")
    ResponseEntity<FilmDto> addFilm(@RequestBody @Valid NewFilmDto filmDto) {
        var createdFilm = service.addFilm(
                filmDto.getFilmName(), filmDto.getYear(), filmDto.getBoxOffice(), filmDto.getGenre());
        return new ResponseEntity<>(
                modelMapper.map(createdFilm, FilmDto.class),
                HttpStatus.CREATED
        );
    }

    // "/api/films/{id}"
    @GetMapping("/api/extraFilmInfo/{id}")
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

    @GetMapping("/api/extraFilmInfo/{id}/actors")
    ResponseEntity<List<ActorDto>> getActorsOfFilms(@PathVariable("id") int filmId) {
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
                .map(actor -> modelMapper.map(actor, ActorDto.class))
                .toList());
    }

    // "/api/actors"
    @GetMapping("/api/films")
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
    @DeleteMapping("/api/extraFilmInfo/{id}")
    ResponseEntity<Void> deleteFilm(@PathVariable("id") int filmId) {
        if (service.deleteFilm(filmId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // "/api/issues/{id}"
    @PatchMapping("/api/extraFilmInfo/{id}")
    ResponseEntity<Void> changeIssue(@PathVariable("id") int filmId,
                                     @RequestBody @Valid UpdateFilmDto updateFilmInfo) {
        if (service.changeFilmInfo(filmId,updateFilmInfo.getBoxOffice(), updateFilmInfo.getGenre(), updateFilmInfo.getYear())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
