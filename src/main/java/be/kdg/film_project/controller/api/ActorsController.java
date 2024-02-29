package be.kdg.film_project.controller.api;

import be.kdg.film_project.controller.api.dto.ActorDto;
import be.kdg.film_project.controller.api.dto.FilmDto;
import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.domain.FilmCasting;
import be.kdg.film_project.service.ActorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorsController {
    private final ActorService service;

    public ActorsController(ActorService service) {
        this.service = service;
    }

    // "/api/actors/{id}"
    @GetMapping("{id}")
    ResponseEntity<ActorDto> getOneActor(@PathVariable("id") int actorId) {
        var actor = service.getActor(actorId);
        if (actor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(
                new ActorDto(
                        actor.getActorName(),
                        actor.getGender(),
                        actor.getNationality(),
                        actor.getId()
                ));
    }


    // "/api/actors/1/films"
    @GetMapping("{id}/films")
    ResponseEntity<List<FilmDto>> getFilmsOfActor(@PathVariable("id") int actorId) {
        var actor = service.getActorWithFilms(actorId);
        if (actor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (actor.getFilm().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(actor.getFilm()
                .stream()
                .map(FilmCasting::getFilm)
                .map(film -> new FilmDto(
                        film.getFilmName(),
                        film.getYear(),
                        film.getBoxOffice(),
                        film.getGenre(),
                        film.getId()
                )).toList());
    }

    // "/api/actors"
    @GetMapping
    ResponseEntity<List<ActorDto>> searchActor(@RequestParam(required = false, value = "gender") Actor.Gender gender,
                                               @RequestParam(required = false, value = "nationality") String nationality) {
        if (gender == null && nationality == null) {
            return ResponseEntity
                    .ok(service.getActors()
                            .stream()
                            .map(actor -> new ActorDto(
                                    actor.getActorName(),
                                    actor.getGender(),
                                    actor.getNationality(),
                                    actor.getId()))
                            .toList());
        } else {
            var searchResult = service.getByGenderAndNationality(gender, nationality);
            if (searchResult.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.ok(searchResult
                        .stream()
                        .map(actor -> new ActorDto(
                                actor.getActorName(),
                                actor.getGender(),
                                actor.getNationality(),
                                actor.getId()))
                        .toList());
            }
        }
    }

    // "/api/actors/{id}"
    @DeleteMapping("{id}")
    ResponseEntity<Void> deleteActor(@PathVariable("id") int actorId) {
        if (service.deleteActor(actorId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
