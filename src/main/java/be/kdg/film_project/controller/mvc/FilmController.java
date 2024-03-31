package be.kdg.film_project.controller.mvc;


import be.kdg.film_project.domain.Film;
import be.kdg.film_project.presentation.exceptions.FilmException;
import be.kdg.film_project.controller.mvc.viewmodels.FilmViewModel;
import be.kdg.film_project.security.CustomUserDetails;
import be.kdg.film_project.service.FilmService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static be.kdg.film_project.domain.UserRole.ADMIN;

@Controller
public class FilmController {
    private final FilmService filmService;
    private Logger logger = LoggerFactory.getLogger(FilmController.class);

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public ModelAndView allFilms(HttpServletRequest request){
        var mav = new ModelAndView();
        mav.setViewName("films");
        mav.addObject("all_films",
                filmService.getFilms()
                        .stream()
                        .map(film -> new FilmViewModel(
                                film.getId(),
                                film.getFilmName(),
                                film.getGenre(),
                                film.getBoxOffice(),
                                film.getYear(),
                                request.isUserInRole(ADMIN.getCode())
                        ))
                        .toList());
        return mav;
    }

    @GetMapping("/extraFilmInfo")
    public ModelAndView oneFilm(@RequestParam("id") int filmId,
                                @AuthenticationPrincipal CustomUserDetails user,
                                HttpServletRequest request) {
        var film = filmService.getFilm(filmId);
        var mav = new ModelAndView();
        mav.setViewName("extraFilmInfo");
        mav.addObject("one_film",
                new FilmViewModel(
                        film.getId(),
                        film.getFilmName(),
                        film.getGenre(),
                        film.getBoxOffice(),
                        film.getYear(),
                        user != null && (request.isUserInRole(ADMIN.getCode())
                        )));
        return mav;
    }

    @GetMapping("/films/search")
    public ModelAndView searchFilms(@RequestParam(required = false, value = "actorsName") String actorsName) {
        var mav = new ModelAndView("films");
        List<Film> films;
        if (actorsName != null && !actorsName.isEmpty()) {
            films = filmService.getByActors(actorsName);
        } else {
            films = filmService.getFilms();
        }
        List<FilmViewModel> filmViewModels = films.stream()
                .map(film -> new FilmViewModel(
                        film.getId(),
                        film.getFilmName(),
                        film.getGenre(),
                        film.getBoxOffice(),
                        film.getYear()
                )).toList();

        mav.addObject("all_films", filmViewModels);
        return mav;
    }

    @GetMapping("/addFilm")
    public String getAddFilm(Model model) {
        model.addAttribute("film", new FilmViewModel());
        return "/addFilm";
    }

//    @PostMapping("/extraFilmInfo/update")
//    public String updateFilm(@Valid UpdateFilmViewModel filmViewModel,
//                                  BindingResult bindingResult,
//                                  @AuthenticationPrincipal CustomUserDetails user,
//                                  HttpServletRequest request) {
//        // Conditions:
//        // - The user executing the action is the same as the developer whose email is being updated
//        //   - OR The user is an admin
//        // - AND no model binding errors
//        if ((request.isUserInRole(ADMIN.getCode()))
//                && (!bindingResult.hasErrors())) {
//            filmService.updateFilmInfo(
//                    filmViewModel.getId(),
//                    filmViewModel.getBoxOffice(),
//                    filmViewModel.getGenre(),
//                    filmViewModel.getYear());
//        }
//        return "redirect:/extraFilmInfo?id=" + filmViewModel.getId();
//    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FilmException.class)
    public String handlePageException(Exception e, Model model) {
        logger.error("Error in exceptionHandler" + e);
        model.addAttribute("error", "The page is not found ;(");
        return "error";
    }
}

