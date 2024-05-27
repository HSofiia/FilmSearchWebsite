package be.kdg.film_project.controller.api;

import be.kdg.film_project.controller.api.dto.director.DirectorDto;
import be.kdg.film_project.controller.api.dto.director.NewDirectorDto;
import be.kdg.film_project.controller.api.dto.director.UpdateDirectorDto;
import be.kdg.film_project.controller.api.dto.film.FilmDto;
import be.kdg.film_project.controller.api.dto.film.UpdateFilmDto;
import be.kdg.film_project.security.CustomUserDetails;
import be.kdg.film_project.service.DirectorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static be.kdg.film_project.domain.UserRole.ADMIN;

@RestController
public class DirectorsController {
    private final DirectorService service;
    private final ModelMapper modelMapper;

    public DirectorsController(DirectorService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/api/addDirector")
    ResponseEntity<DirectorDto> addFilm(@RequestBody @Valid NewDirectorDto directorDto, @AuthenticationPrincipal CustomUserDetails user) {
        if(user.getAuthorities().stream().anyMatch(aut -> aut.getAuthority().equals("ROLE_ADMIN") || aut.getAuthority().equals("ROLE_USER"))) {
            var createdDirector = service.addDirector(
                    directorDto.getDirectorName(), directorDto.getBirth(), directorDto.getAward());
            return new ResponseEntity<>(
                    modelMapper.map(createdDirector, DirectorDto.class),
                    HttpStatus.CREATED
            );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/api/extraDirectorInfo/{id}")
    ResponseEntity<DirectorDto> getOneDirector(@PathVariable("id") int directorId) {
        var director = service.getDirectorById(directorId);
        if (director == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(modelMapper.map(director, DirectorDto.class));
    }

//    @GetMapping("/api/extraDirectorInfo/{id}/films")
//    ResponseEntity<List<FilmDto>> getFilmsOfDirector(@PathVariable("id") int directorId) {
//        var director = service.getDirectorWithFilms(directorId);
//        if (director == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(director.getFilm()
//                .stream()
//                .map(film -> modelMapper.map(film, FilmDto.class))
//                .toList());
//    }

    @GetMapping("/api/directors")
    ResponseEntity<List<DirectorDto>> searchDirector(@RequestParam(required = false, value = "award") String award) {
        if (award == null) {
            return ResponseEntity.ok(service.getDirectors()
                    .stream()
                    .map(director -> modelMapper.map(director, DirectorDto.class)).toList());
        } else {
            var searchResult = service.getByAward(award);
            if (searchResult.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.ok(searchResult
                        .stream()
                        .map(director -> modelMapper.map(director, DirectorDto.class)).toList());
            }
        }
    }


    @DeleteMapping("/api/extraDirectorInfo/{id}")
    ResponseEntity<Void> deleteDirector(@PathVariable("id") int directorId,
                                    HttpServletRequest request) {
        if (!request.isUserInRole(ADMIN.getCode())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (service.deleteDirector(directorId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/api/extraDirectorInfo/{id}")
    ResponseEntity<Void> changeDirector(@PathVariable("id") int directorId,
                                     @RequestBody @Valid UpdateDirectorDto updateDirectorInfo,
                                     HttpServletRequest request) {
        if (!request.isUserInRole(ADMIN.getCode())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (service.changeDirectorInfo(directorId, updateDirectorInfo.getBirth(), updateDirectorInfo.getAward())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
