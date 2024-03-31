package be.kdg.film_project.controller.api;

import be.kdg.film_project.controller.api.dto.actor.ActorDto;
import be.kdg.film_project.controller.api.dto.actor.NewActorDto;
import be.kdg.film_project.controller.api.dto.actor.UpdateActorDto;
import be.kdg.film_project.controller.api.dto.film.FilmDto;
import be.kdg.film_project.domain.Actor;
import be.kdg.film_project.domain.FilmCasting;
import be.kdg.film_project.service.ActorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static be.kdg.film_project.domain.UserRole.ADMIN;

@RestController
//@RequestMapping("/api/actors")
public class ActorsController {
    private final ActorService service;
    private final ModelMapper modelMapper;


    public ActorsController(ActorService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    // "/api/issues"
    @PostMapping("/api/addActor")
    ResponseEntity<ActorDto> addActor(@RequestBody @Valid NewActorDto actorDto) {
        var createdActor = service.addActor(
                actorDto.getActorName(), actorDto.getGender(), actorDto.getNationality());
        return new ResponseEntity<>(
                modelMapper.map(createdActor, ActorDto.class),
                HttpStatus.CREATED
        );
    }

    // "/api/actors/{id}"
    @GetMapping("api/extraActorInfo/{id}")
    ResponseEntity<ActorDto> getOneActor(@PathVariable("id") int actorId) {
        var actor = service.getActor(actorId);
        if (actor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(
                modelMapper.map(actor, ActorDto.class));
    }


    // "/api/actors/1/films"
    @GetMapping("/api/extraActorInfo/{id}/films")
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
                .map(film -> modelMapper.map(film, FilmDto.class))
                .toList());
    }

    // "/api/actors"
    @GetMapping("/api/actors")
    ResponseEntity<List<ActorDto>> searchActor(@RequestParam(required = false, value = "gender") Actor.Gender gender,
                                               @RequestParam(required = false, value = "nationality") String nationality) {
        if (gender == null && nationality == null) {
            return ResponseEntity
                    .ok(service.getActors()
                            .stream()
                            .map(actor -> modelMapper.map(actor, ActorDto.class))
                            .toList());
        } else {
            var searchResult = service.getByGenderAndNationality(gender, nationality);
            if (searchResult.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.ok(searchResult
                        .stream()
                        .map(actor -> modelMapper.map(actor, ActorDto.class))
                        .toList());
            }
        }
    }

    // "/api/actors/{id}"
    @DeleteMapping("/api/extraActorInfo/{id}")
    ResponseEntity<Void> deleteActor(@PathVariable("id") int actorId,
                                     HttpServletRequest request) {
        if (!request.isUserInRole(ADMIN.getCode())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (service.deleteActor(actorId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/api/extraActorInfo/{id}")
    ResponseEntity<Void> changeIssue(@PathVariable("id") int actorId,
                                     @RequestBody @Valid UpdateActorDto updateActorInfo,
                                     HttpServletRequest request) {
        if (!request.isUserInRole(ADMIN.getCode())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (service.updateActorInfo(actorId,updateActorInfo.getGender(), updateActorInfo.getNationality())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
